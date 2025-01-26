package com.github.ku4marez.clinicmanagement.repository;

import com.github.ku4marez.clinicmanagement.entity.PatientEntity;
import com.github.ku4marez.clinicmanagement.util.CreateEntityUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
@ActiveProfiles("test")
class PatientRepositoryTest {

    @Autowired
    private PatientRepository patientRepository;

    @BeforeEach
    void setUp() {
        patientRepository.deleteAll();
    }

    @Test
    void testFindByMedicalRecordNumber_Success() {
        PatientEntity patient = CreateEntityUtil.createDefaultPatientEntity();
        patientRepository.save(patient);

        Optional<PatientEntity> result = patientRepository.findByMedicalRecordNumber(CreateEntityUtil.PATIENT_RECORD_NUMBER);

        assertTrue(result.isPresent());
        assertEquals(CreateEntityUtil.PATIENT_FIRST_NAME, result.get().getFirstName());
    }

    @Test
    void testFindByMedicalRecordNumber_NotFound() {
        Optional<PatientEntity> result = patientRepository.findByMedicalRecordNumber("NON_EXISTENT_MRN");

        assertFalse(result.isPresent());
    }

    @Test
    void testSearchByName_Success() {
        PatientEntity patient1 = CreateEntityUtil.createPatientEntity("1", "Emily", "White", "emily.white@example.com", LocalDate.of(1995, 5, 15),
                "1234567890", "456 Elm St", "MRN654321");
        PatientEntity patient2 = CreateEntityUtil.createPatientEntity("2", "Emma", "Black", "emma.black@example.com", LocalDate.of(1992, 3, 10),
                "9876543210", "789 Pine St", "MRN789123");
        patientRepository.save(patient1);
        patientRepository.save(patient2);

        Pageable pageable = PageRequest.of(0, 10);
        Page<PatientEntity> result = patientRepository.searchByName("Em", pageable);

        assertEquals(2, result.getTotalElements());
        assertEquals("Emily", result.getContent().get(0).getFirstName());
    }
}

