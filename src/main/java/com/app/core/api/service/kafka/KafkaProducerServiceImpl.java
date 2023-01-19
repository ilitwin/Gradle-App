package com.app.core.api.service.kafka;

import com.app.core.api.model.LeadCampaignFlow;
import com.app.core.api.model.constant.Topic;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class KafkaProducerServiceImpl implements KafkaProducerService {

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    public void sendToCoreActionTopic(LeadCampaignFlow leadCampaignFlow) {

        log.info("Sending Message to Kafka CORE_SCHEDULER topic: {} - {}", leadCampaignFlow.getId(), System.nanoTime());

        this.kafkaTemplate.send(Topic.CORE_SCHEDULER, leadCampaignFlow.getId(), leadCampaignFlow);
    }

    public void sendToCoreCreatorTopic(LeadCampaignFlow leadCampaignFlow) {

        log.info("Sending Message to Kafka CORE_CREATOR topic: {} - {}", leadCampaignFlow.getId(), System.nanoTime());

        this.kafkaTemplate.send(Topic.CORE_CREATOR, leadCampaignFlow.getId(), leadCampaignFlow);
    }
}
