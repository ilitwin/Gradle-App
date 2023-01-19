package com.app.core.api.config;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import lombok.extern.slf4j.Slf4j;
import org.bson.UuidRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

@Slf4j
@Configuration
public class MongoConfig {


    @Value("${spring.data.mongodb.database}")
    private String databaseName;

    @Value("${spring.data.mongodb.uri}")
    private String connectionUri;

    @Value("${spring.data.mongodb.uuid-representation}")
    private UuidRepresentation uuidRepresentation;

    @Bean
    public MongoClient mongo() {
        ConnectionString connectionString = new ConnectionString(connectionUri);
        MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
                .uuidRepresentation(uuidRepresentation)
                .applyConnectionString(connectionString)
                .build();

        return MongoClients.create(mongoClientSettings);
    }

    @Bean
    public MongoTemplate mongoTemplate() {
        return new MongoTemplate(mongo(), databaseName);
    }
}
