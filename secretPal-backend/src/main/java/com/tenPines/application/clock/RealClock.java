package com.tenPines.application.clock;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@Profile("!test")
public class RealClock implements Clock {

    @Override
    public LocalDate now() {
        return LocalDate.now();
    }
}
