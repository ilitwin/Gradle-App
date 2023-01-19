package com.app.core.api.service.model;

import com.app.core.api.dao.LeadCampaignFlowRepository;
import com.app.core.api.model.LeadCampaignFlow;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class LeadCampaignFlowModelService extends BaseModelService<LeadCampaignFlow, LeadCampaignFlowRepository> {

    public LeadCampaignFlowModelService(LeadCampaignFlowRepository leadCampaignFlowRepository) {
        super(leadCampaignFlowRepository);
    }
}
