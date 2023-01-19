package com.app.core.api.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.config.SaslConfigs;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Configuration
public class KafkaProducerConfig {

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
    public ProducerFactory<String, Object> producerFactory() {
        Map<String, Object> configProps = new HashMap<>();

        configProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);

        if(!"local".equals(springProfilesActive)){
            configProps.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, securityProtocol);
            configProps.put(SaslConfigs.SASL_JAAS_CONFIG, saslJaasConfig);
            configProps.put(SaslConfigs.SASL_MECHANISM, saslMechanism);
            configProps.put(CommonClientConfigs.CLIENT_DNS_LOOKUP_CONFIG, clientDnsLookup);
        }

        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

        // Safer producer
        configProps.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, Boolean.TRUE);
        configProps.put(ProducerConfig.ACKS_CONFIG, "all"); // Could be "1" for decrease latency
        configProps.put(ProducerConfig.RETRIES_CONFIG, Integer.MAX_VALUE);
        configProps.put(ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION, "5"); // Kafka 2.0 >= 1.1 so we can keep as 5. Use 1 otherwise

        // High throughput producer (at the expense of a bit of latency an CPU usage)
        // For smaller batches and decrease latency, "gzip" offers the best compression but at higher
        // CPU cost
        configProps.put(ProducerConfig.COMPRESSION_TYPE_CONFIG, "lz4");
        configProps.put(ProducerConfig.LINGER_MS_CONFIG, 50); // 50 milliseconds
        configProps.put(ProducerConfig.BATCH_SIZE_CONFIG, 64 * 1024); // 64KB batch size
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public KafkaTemplate<String, Object> kafkaTemplate() {
        return new KafkaTemplate<String, Object>(producerFactory());
    }
}
