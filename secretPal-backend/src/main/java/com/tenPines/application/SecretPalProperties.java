package com.tenPines.application;

import org.springframework.context.annotation.Configuration;

@Configuration
public class SecretPalProperties {
    private long reminderDayPeriod = 7L;

    public long getReminderDayPeriod() {
        return reminderDayPeriod;
    }

    public void setReminderDayPeriod(long reminderDayPeriod) {
        this.reminderDayPeriod = reminderDayPeriod;
    }
}
