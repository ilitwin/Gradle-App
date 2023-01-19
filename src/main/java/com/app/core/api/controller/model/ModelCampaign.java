package com.app.core.api.controller.model;

import com.app.core.api.model.Campaign;
import com.app.core.api.service.model.CampaignModelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/model/campaign")
public class ModelCampaign extends BaseModelController<Campaign, CampaignModelService> {

    public ModelCampaign(CampaignModelService campaignCrudService) {
        super(campaignCrudService);
    }
}
