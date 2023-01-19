package com.app.core.api.service.model;

import com.app.core.api.dao.CampaignRepository;
import com.app.core.api.model.Campaign;
import com.app.core.api.service.utils.ClockUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class CampaignModelService extends BaseModelService<Campaign, CampaignRepository> {

    public CampaignModelService(CampaignRepository campaignRepository) {
        super(campaignRepository);
    }

    @Override
    public Campaign update(String id, Campaign campaign) {
        if(this.crudRepository.existsById(id)){
            campaign.setId(id);
            Optional<Campaign> existingRecord = this.crudRepository.findById(id);
            if (existingRecord.get().getUuid() == null) {
                campaign.setUuid(UUID.randomUUID());
            }
            else {
                campaign.setUuid(existingRecord.get().getUuid());
            }
            return this.crudRepository.save(campaign);
        } else {
            return null;
        }
    }

    @Override
    public Campaign insert(Campaign campaign) {
        if (campaign.getUuid() == null) {
            campaign.setUuid(UUID.randomUUID());
        }
        if(campaign.getCreatedDate() == null){
            campaign.setCreatedDate(ClockUtils.getNow());
        }
        return this.crudRepository.insert(campaign);
    }

}
