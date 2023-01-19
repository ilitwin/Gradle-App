package com.app.core.api.dao;

import com.app.core.api.model.LeadCampaignFlow;
import java.util.Date;

public interface LeadCampaignFlowRepositoryCustom {

    long updateInteractionInFlightTime(Date now, Date minuteAgo);

    LeadCampaignFlow findNextLeadCampaignFlow(String leadId, int order);

    long updateTriggerAt(String id, Date now);
}
