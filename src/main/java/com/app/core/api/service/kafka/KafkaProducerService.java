package com.app.core.api.service.kafka;

import com.app.core.api.model.LeadCampaignFlow;

public interface KafkaProducerService {

    void sendToCoreActionTopic(final LeadCampaignFlow leadCampaignFlow);

    void sendToCoreCreatorTopic(final LeadCampaignFlow leadCampaignFlow);
}
