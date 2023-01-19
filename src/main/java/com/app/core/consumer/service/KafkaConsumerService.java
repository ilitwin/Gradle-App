package com.app.core.consumer.service;

import com.app.core.api.exception.AppSchedulingException;
import com.app.core.api.model.LeadCampaignFlow;
import com.app.core.api.model.constant.Topic;
import com.app.core.api.service.model.LeadCampaignFlowModelService;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Slf4j
@Service
public class KafkaConsumerService {

    @Value(value = "${core.base.internal.url}")
    private String coreBaseInternalUrl;

    private LeadCampaignFlowModelService leadCampaignFlowModelService;

    public KafkaConsumerService(LeadCampaignFlowModelService leadCampaignFlowModelService) {
        this.leadCampaignFlowModelService = leadCampaignFlowModelService;
    }

//    @SneakyThrows
    @KafkaListener(topics = Topic.CORE_SCHEDULER, groupId = "CORE_SCHEDULER_GROUP", concurrency = "1")
    public void listenScheduleAction(ConsumerRecord<String, LeadCampaignFlow> cr) throws IOException, InterruptedException {

        var leadCampaignFlow = cr.value();

        log.info("Message Received From Kafka: {} - {}", leadCampaignFlow.getId(), System.nanoTime());

        String url = String.format("%s/scheduler/process/leadCampaignFlow/%s/interaction/%s", coreBaseInternalUrl, leadCampaignFlow.getId(), leadCampaignFlow.getInteractionId());

        log.info("Sending Requests to: {}", url);

        var client = HttpClient.newHttpClient();

        var request = HttpRequest.newBuilder(URI.create(url))
                .header("accept", "application/json")
                .build();

        var response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if(response.statusCode() != 200){
            throw new AppSchedulingException(String.format("Error: %s - %s", response.uri(), response.body()));
        }

        System.out.println(response);
    }

    @KafkaListener(topics = Topic.CORE_CREATOR, groupId = "CORE_CREATOR_GROUP", concurrency = "1")
    public void listenCreateAction(ConsumerRecord<String, LeadCampaignFlow> cr) {

        var leadCampaignFlow = cr.value();
        var newRequest = leadCampaignFlowModelService.insert(leadCampaignFlow);
        log.info("Saved Request: {}", newRequest);
    }
}
