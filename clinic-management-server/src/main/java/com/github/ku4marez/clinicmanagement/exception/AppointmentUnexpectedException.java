package com.github.ku4marez.clinicmanagement.exception;

import java.time.LocalDateTime;

public class AppointmentUnexpectedException extends RuntimeException {

    public AppointmentUnexpectedException(String exceptionMessage, LocalDateTime dateTime) {
        super(exceptionMessage + dateTime);
    }
}
