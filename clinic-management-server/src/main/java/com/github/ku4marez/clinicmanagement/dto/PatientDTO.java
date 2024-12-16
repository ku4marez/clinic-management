package com.github.ku4marez.clinicmanagement.dto;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record PatientDTO(
        String firstName,
        String lastName,
        String email,
        LocalDate dateOfBirth,
        String phoneNumber,
        String address,
        String medicalRecordNumber
) {}
