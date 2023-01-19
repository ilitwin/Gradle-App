package com.app.core.controller;

import com.app.core.api.controller.model.ModelLeadCampaignFlow;
import com.app.core.api.model.LeadCampaignFlow;
import com.app.core.api.service.kafka.KafkaProducerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ModelLeadCampaignFlowTest {

    @Mock
    KafkaProducerService kafkaProducerService;

    @InjectMocks
    ModelLeadCampaignFlow modelLeadCampaignFlowController;

    @Test
    public void shouldCallKafkaProducerService() {
        LeadCampaignFlow mockFlow = new LeadCampaignFlow();
        modelLeadCampaignFlowController.insert(mockFlow);
        verify(kafkaProducerService, times(1)).sendToCoreCreatorTopic(mockFlow);
    }

}