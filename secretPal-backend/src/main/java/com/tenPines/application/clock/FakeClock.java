package com.tenPines.application.clock;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@Profile("test")
public class FakeClock implements Clock {
    private LocalDate lies;

    public FakeClock() {
        this.lies = LocalDate.now();
    }

    public FakeClock(LocalDate lie) {
        this.lies = lie;
    }

    @Override
    public LocalDate now() {
        return lies;
    }

    public void setTime(LocalDate lies) {
        this.lies = lies;
    }
}
