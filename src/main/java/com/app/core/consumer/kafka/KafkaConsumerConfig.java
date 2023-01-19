package com.app.core.consumer.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.config.SaslConfigs;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.retry.RetryPolicy;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.policy.AlwaysRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Configuration
public class KafkaConsumerConfig {

    @Value(value = "${spring.profiles.active}")
    private String springProfilesActive;

    @Value(value = "${bootstrap.servers}")
    private String bootstrapAddress;

    @Value(value = "${security.protocol}")
    private String securityProtocol;

    @Value(value = "${sasl.jaas.config}")
    private String saslJaasConfig;

    @Value(value = "${sasl.mechanism}")
    private String saslMechanism;

    @Value(value = "${client.dns.lookup}")
    private String clientDnsLookup;

    @Bean
    public ConsumerFactory<String, String> consumerFactory() {
        Map<String, Object> props = new HashMap<>();

        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);

        if(!"local".equals(springProfilesActive)){
            props.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, securityProtocol);
            props.put(SaslConfigs.SASL_JAAS_CONFIG, saslJaasConfig);
            props.put(SaslConfigs.SASL_MECHANISM, saslMechanism);
            props.put(CommonClientConfigs.CLIENT_DNS_LOOKUP_CONFIG, clientDnsLookup);
        }

        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "com.app.core.api.model");
        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 500);
        return new DefaultKafkaConsumerFactory<>(props);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        factory.setRetryTemplate(retryTemplate());
//        factory.setReplyTemplate(kafkaTemplate);
        return factory;
    }

    @Bean
    public RetryTemplate retryTemplate(){
        RetryTemplate retryTemplate = new RetryTemplate();

        FixedBackOffPolicy backOffPolicy = new FixedBackOffPolicy();
        backOffPolicy.setBackOffPeriod(10000);
        retryTemplate.setBackOffPolicy(backOffPolicy);

        RetryPolicy retryPolicy = new AlwaysRetryPolicy();
        retryTemplate.setRetryPolicy(retryPolicy);

        return retryTemplate;
    }
}
