package com.github.ku4marez.clinicmanagement.service;

import com.github.ku4marez.commonlibraries.dto.PatientDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface PatientService {
    Page<PatientDTO> searchPatient(String name, Pageable pageable);

    PatientDTO createPatient(PatientDTO patient);

    Optional<PatientDTO> getPatientByRecordNumber(String recordNumber);

    Page<PatientDTO> getAllPatients(Pageable pageable);

    PatientDTO updatePatient(String id, PatientDTO patient);

    void deletePatient(String id);
}
