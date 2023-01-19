package com.app.core.api.config;

import com.app.core.api.model.LeadCampaignFlow;
import com.app.core.api.service.model.LeadCampaignFlowModelService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import javax.annotation.PostConstruct;
import java.util.Date;

@Configuration
public class DBInitConfig {

    private final LeadCampaignFlowModelService leadCampaignFlowModelService;

    public DBInitConfig(
            LeadCampaignFlowModelService leadCampaignFlowModelService) {
        this.leadCampaignFlowModelService = leadCampaignFlowModelService;
    }

    @SneakyThrows
    @PostConstruct
    public void initDb(){

        //Importing Messages
        Resource leadResource = new ClassPathResource("/data/demo/LeadCampaignFlow.json");

        ObjectMapper mapper = new ObjectMapper();

        var leadCampaignFlow = mapper.readValue(leadResource.getFile(), LeadCampaignFlow.class);
        leadCampaignFlow.setInteractionTriggerTime(new Date());

        for(int i=0; i<120; i++){

            leadCampaignFlow.setId(null);
            leadCampaignFlow.setInteractionTriggerTime(DateUtils.addMinutes(leadCampaignFlow.getInteractionTriggerTime(), 1));

            this.leadCampaignFlowModelService.save(leadCampaignFlow);

        }

    }
}
