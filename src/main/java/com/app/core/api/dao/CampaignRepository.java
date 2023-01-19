package com.app.core.api.dao;

import com.app.core.api.model.Campaign;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CampaignRepository extends MongoRepository<Campaign, String> {}
