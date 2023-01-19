package com.app.core.module;

import com.app.core.api.service.utils.ClockUtils;
import org.springframework.context.annotation.Configuration;
import javax.annotation.PostConstruct;
import java.util.TimeZone;

@Configuration
public class TimezoneConfig {

    @PostConstruct
    public void init(){

        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        System.out.println("Date in UTC: " + ClockUtils.getNow().toString());
    }
}
