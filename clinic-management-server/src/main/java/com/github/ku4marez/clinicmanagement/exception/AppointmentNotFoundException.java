package com.github.ku4marez.clinicmanagement.exception;

public class AppointmentNotFoundException extends EntityNotFoundException {

    public AppointmentNotFoundException(String identifier) {
        super("Appointment", identifier);
    }
}
