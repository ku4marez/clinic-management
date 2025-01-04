package com.github.ku4marez.clinicmanagement.exception;

import com.github.ku4marez.commonlibraries.entity.exception.EntityNotFoundException;

public class PatientNotFoundException extends EntityNotFoundException {

    public PatientNotFoundException(String identifier) {
        super("Patient", identifier);
    }
}
