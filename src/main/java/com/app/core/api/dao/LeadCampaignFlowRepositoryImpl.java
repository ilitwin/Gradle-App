package com.app.core.api.dao;

import com.app.core.api.model.LeadCampaignFlow;
import com.mongodb.client.result.UpdateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import java.util.Date;

public class LeadCampaignFlowRepositoryImpl implements LeadCampaignFlowRepositoryCustom {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public long updateInteractionInFlightTime(Date now, Date minuteAgo) {

        Query query = new Query();

        Criteria firstCriteria = new Criteria();
        firstCriteria.andOperator(
                Criteria.where("interactionTriggerTime").lte(now),
                Criteria.where("interactionInFlightTime").is(null),
                Criteria.where("endTime").is(null));

        Criteria secondCriteria = new Criteria();
        secondCriteria.andOperator(
                Criteria.where("interactionInFlightTime").lte(minuteAgo),
                Criteria.where("endTime").is(null));

        Criteria orCriteria = new Criteria();
        orCriteria.orOperator(firstCriteria, secondCriteria);

        query.addCriteria(orCriteria);

        Update update = new Update();
        update.set("interactionInFlightTime", now);

        UpdateResult result = mongoTemplate.updateMulti(query, update, LeadCampaignFlow.class);

        return result.getModifiedCount();
    }

    @Override
    public LeadCampaignFlow findNextLeadCampaignFlow(String leadId, int order) {

        Query query = new Query();
        query.addCriteria(Criteria.where("leadId").is(leadId));
        query.addCriteria(Criteria.where("order").lt(order));
        query.addCriteria(Criteria.where("startTime").exists(false));
        query.with(Sort.by(Sort.Direction.DESC, "order"));

        return mongoTemplate.find(query, LeadCampaignFlow.class).stream().findFirst().orElse(null);
    }

    @Override
    public long updateTriggerAt(String id, Date now) {

        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id));

        Update update = new Update();
        update.set("triggerAt", now);

        UpdateResult result = mongoTemplate.updateFirst(query, update, LeadCampaignFlow.class);

        return result.getModifiedCount();
    }
}
