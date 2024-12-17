package com.github.ku4marez.clinicmanagement.exception;

public class DoctorNotFoundException extends EntityNotFoundException {

    public DoctorNotFoundException(String identifier) {
        super("Doctor", identifier);
    }
}
