package com.app.core.api.service.scheduler;

import com.app.core.api.dao.LeadCampaignFlowRepository;
import com.app.core.api.model.LeadCampaignFlow;
import com.app.core.api.service.kafka.KafkaProducerService;
import com.app.core.api.service.utils.ClockUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SchedulerService {

    private LeadCampaignFlowRepository leadCampaignFlowRepository;

    private KafkaProducerService kafkaProducerService;
    
    

    public SchedulerService(
            LeadCampaignFlowRepository leadCampaignFlowRepository,
            KafkaProducerService kafkaProducerService
    ) {
        this.leadCampaignFlowRepository = leadCampaignFlowRepository;
        this.kafkaProducerService = kafkaProducerService;
    }

//    @Async
    public void processScheduledActions() {

        var now = ClockUtils.getNow();
        var minuteAgo = DateUtils.addMinutes(now,-1);

        long updatedItems = leadCampaignFlowRepository.updateInteractionInFlightTime(now, minuteAgo);

        if (updatedItems > 0) {
            log.info("{} Items found", updatedItems);
            var scheduledItemList = leadCampaignFlowRepository.findLeadCampaignFlowByInteractionInFlightTime(now);
            scheduledItemList.forEach(scheduledItem -> kafkaProducerService.sendToCoreActionTopic(scheduledItem));
        }
//        else{
//            log.info("////No Items found!");
//        }
    }

    public void processScheduledAction(String leadCampaignFlowId, Integer interactionId) {

        log.info("Processing Request: {} - {} - {}", leadCampaignFlowId, interactionId, System.nanoTime());
        // Querying for ScheduledAction and making sure is not completed but other process
        LeadCampaignFlow leadCampaignFlow = this.leadCampaignFlowRepository.findByIdAndInteractionId(leadCampaignFlowId, interactionId);

        if (leadCampaignFlow != null) {
            log.info("Executing LeadCampaignFlow");
        }
    }
}
