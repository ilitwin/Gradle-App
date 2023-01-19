package com.app.core.api.dao;

import com.app.core.api.model.LeadCampaignFlow;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import java.util.Date;
import java.util.List;

public interface LeadCampaignFlowRepository extends MongoRepository<LeadCampaignFlow, String>, LeadCampaignFlowRepositoryCustom {

    @Query(fields="{_id : 1, interactionId : 1}")
    List<LeadCampaignFlow> findLeadCampaignFlowByInteractionInFlightTime(Date now);

    LeadCampaignFlow findByIdAndInteractionId(String id, Integer interactionId);
}
