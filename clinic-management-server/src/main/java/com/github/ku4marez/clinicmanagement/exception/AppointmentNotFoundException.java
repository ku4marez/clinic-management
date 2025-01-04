package com.github.ku4marez.clinicmanagement.exception;

import com.github.ku4marez.commonlibraries.entity.exception.EntityNotFoundException;

public class AppointmentNotFoundException extends EntityNotFoundException {

    public AppointmentNotFoundException(String identifier) {
        super("Appointment", identifier);
    }
}
