# App 

This is a working app that can serve as a template for developing applications with [Java](https://www.java.com/en/), [Gradle](https://gradle.org), [Spring.io](https://spring.io/), [Kafka](https://kafka.apache.org/), [MongoDb](https://www.mongodb.com/) and [Apache ZooKeeper](https://zookeeper.apache.org) dependencies.

## Getting Started

This app is built on top of Docker and Docker Compose to ease the process of setting up multiple services and connecting them.

To get started, make sure that Docker and Docker Compose are installed. Then:

1. Open application with y IDE.
2. Run `docker-compose up -d` to start up mongodb and kafka servers
3. Run CoreApplication to start app

## Architecture

### Database

App's data persistence layer is built using Mongo. Additionally, Kafka server is used for communication between different micro-services.

Mongo database can be found at `http://localhost:27017`. Kafka server runs on `localhost:9092`

On start of the application the database will be populated with a few LeadCampaignFlow records for automatic execution.

### Modules

In this application there are 3 modules. 

- API module (src/java/com/app/core/api)
- Consumer module (src/java/com/app/core/consumer)
- Cron module (src/java/com/app/core/cron)

### API (com.app.core.api)

API module contains all endpoints into the application it also acts as a Kafka producer whenever it needs to send a message to an external service.

When the application starts you can view available APIs on [http://localhost:8080/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config](http://localhost:8080/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config)

### Consumer (com.app.core.consumer)

This module consumes data from kafka server and acts as a broker sending those request via REST call to the API module for execution.

### Cron (com.app.core.cron)

This service pings API module every second looking for records that need execution.

### Application Flow Through Kafka

When the application starts the Cron module will start pinging the API service every second looking for LeadCampaignFlow records that need execution.
Once a record is found that API module will then send that record to kafka.

The Consumer module will then pick up any records found in kafka and will send a request with the id of that record to be executed back by the API module.

Records should come up for execution every minute.

### Layer Architecture

The API module contains: presentation layer (controllers), service layer and data access object layer (DAO). 

The data models for this module are located under the `com.app.core.api.mode` package, there are two data models LeadCampaignFlow and Campaign

Consumer and cron module only has one service layer

### Testing

JUnit framework is used to test this application. 
