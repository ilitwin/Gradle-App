package com.app.core.service;

import com.app.core.CoreApplicationTests;
import com.app.core.api.model.Campaign;
import com.app.core.api.service.model.CampaignModelService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.UUID;

public class CampaignModelServiceTest extends CoreApplicationTests {

    @Autowired
    private CampaignModelService campaignModelService;

    @Test
    public void testCRUDFunctions(){

        var campaign = new Campaign();
        campaign.setName("NAME_1");
        campaign.setDescription("NAME_1");

        //Testing CREATE
        campaignModelService.insert(campaign);

        assert campaign.getId() != null && campaign.getUuid() != null;

        //Testing READ
        var newObject = campaignModelService.findById(campaign.getId());

        assert newObject.isPresent() && newObject.get().equals(campaign);

        //TESTING UPDATE (UUID being present in new record)
        //Verifying that existing UUIDs don't get overwritten

        var newCampaign = newObject.get();
        newCampaign.setDescription("NAME_2");
        newCampaign.setUuid(UUID.randomUUID());

        campaignModelService.save(newCampaign);

        newObject = campaignModelService.findById(campaign.getId());

        assert newObject.isPresent() && !newObject.get().equals(campaign) && newObject.get().getUuid().equals(newCampaign.getUuid());

        //TESTING UPDATE (UUID not being present in new record)

        var newCampaignWithNullUUID = newObject.get();
        newCampaignWithNullUUID.setDescription("NAME_3");
        newCampaignWithNullUUID.setUuid(null);

        campaignModelService.update(newCampaign.getId(), newCampaignWithNullUUID);

        newObject = campaignModelService.findById(newCampaignWithNullUUID.getId());

        assert newObject.isPresent() && !newObject.get().equals(campaign) && newObject.get().getUuid() != null;

        //Testing DELETE
        campaignModelService.delete(newCampaign.getId());

        newObject = campaignModelService.findById(newCampaign.getId());

        assert newObject.isEmpty();

    }

}