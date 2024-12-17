package com.github.ku4marez.clinicmanagement.exception;

public class PatientNotFoundException extends EntityNotFoundException {

    public PatientNotFoundException(String identifier) {
        super("Patient", identifier);
    }
}
