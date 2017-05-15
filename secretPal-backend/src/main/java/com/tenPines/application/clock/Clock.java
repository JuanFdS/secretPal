package com.tenPines.application.clock;

import java.time.LocalDate;

public interface Clock {
    LocalDate now();

    default int currentYear() {
      return this.now().getYear();
    };
}
