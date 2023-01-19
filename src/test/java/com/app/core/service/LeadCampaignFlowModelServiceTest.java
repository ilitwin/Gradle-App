package com.app.core.service;

import com.app.core.CoreApplicationTests;
import com.app.core.api.model.LeadCampaignFlow;
import com.app.core.api.service.model.LeadCampaignFlowModelService;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

public class LeadCampaignFlowModelServiceTest extends CoreApplicationTests {

    @Autowired
    private LeadCampaignFlowModelService leadCampaignFlowModelService;


    @Test
    public void testCRUDFunctions(){

        var newLCF = new LeadCampaignFlow();
        newLCF.setStartTime(new Date());

        //Testing CREATE
        leadCampaignFlowModelService.save(newLCF);

        assert newLCF.getId() != null;

        //Testing READ
        var newObject = leadCampaignFlowModelService.findById(newLCF.getId());

        assert newObject.isPresent() && newObject.get().equals(newLCF);

        var newLeadCampaign = newObject.get();
        newLeadCampaign.setStartTime(DateUtils.addMinutes(newLeadCampaign.getStartTime(), 3));

        //TESTING UPDATE
        leadCampaignFlowModelService.save(newLeadCampaign);

        newObject = leadCampaignFlowModelService.findById(newLCF.getId());

        assert newObject.isPresent() && !newObject.get().equals(newLCF);

        //Testing DELETE
        leadCampaignFlowModelService.delete(newLCF.getId());

        newObject = leadCampaignFlowModelService.findById(newLCF.getId());

        assert newObject.isEmpty();

    }
}
