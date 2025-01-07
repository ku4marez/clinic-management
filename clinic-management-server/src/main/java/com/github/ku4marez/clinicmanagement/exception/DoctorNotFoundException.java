package com.github.ku4marez.clinicmanagement.exception;

import com.github.ku4marez.commonlibraries.exception.EntityNotFoundException;

public class DoctorNotFoundException extends EntityNotFoundException {

    public DoctorNotFoundException(String identifier) {
        super("Doctor", identifier);
    }
}
