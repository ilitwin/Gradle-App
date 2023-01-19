package com.app.core.api.controller.model;

import com.app.core.api.model.LeadCampaignFlow;
import com.app.core.api.service.kafka.KafkaProducerService;
import com.app.core.api.service.model.LeadCampaignFlowModelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/model/leadCampaignFlow")
public class ModelLeadCampaignFlow extends BaseModelController<LeadCampaignFlow, LeadCampaignFlowModelService> {

    private KafkaProducerService kafkaProducerService;

    public ModelLeadCampaignFlow(LeadCampaignFlowModelService leadCampaignFlowModelService, KafkaProducerService kafkaProducerService) {
        super(leadCampaignFlowModelService);
        this.kafkaProducerService = kafkaProducerService;
    }

    @Override
    @ResponseBody
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity<LeadCampaignFlow> insert(@Valid @RequestBody LeadCampaignFlow leadCampaignFlow) {
        kafkaProducerService.sendToCoreCreatorTopic(leadCampaignFlow);
        return new ResponseEntity<>(leadCampaignFlow, HttpStatus.OK);
    }
}
