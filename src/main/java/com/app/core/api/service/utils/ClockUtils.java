package com.app.core.api.service.utils;

import java.time.Clock;
import java.util.Date;

public class ClockUtils {

    private static Clock clock = Clock.systemDefaultZone();

    public static Clock getClock() {
        return clock;
    }

    public static void setClock(Clock newClock) {
        clock = newClock;
    }

    public static Date getNow() {
        return Date.from(clock.instant());
    }
}
