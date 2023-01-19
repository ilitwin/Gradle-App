package com.app.core.module;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(name = "com.app.core.consumer.enabled", havingValue = "true")
@ComponentScan(basePackages = "com.app.core.consumer")
public class ConsumerModuleConfiguration {}
