package com.app.core.cron;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Slf4j
@Service
@EnableScheduling
public class CronService {

    @Value(value = "${core.base.internal.url}")
    private String coreBaseInternalUrl;

    /**
     * This cron makes a request to the /scheduler/process/actions every 1 second to pick up any actions that need to be executed.
     */
    @SneakyThrows
    @Scheduled(cron = "*/1 * * * * *")
    public void runTheApp() {

        var client = HttpClient.newHttpClient();
        var request = HttpRequest
                .newBuilder(URI.create(getBaseUrl()))
                .build();
        var response = client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    private String getBaseUrl(){
        return coreBaseInternalUrl + "/scheduler/process/actions";
    }
}
