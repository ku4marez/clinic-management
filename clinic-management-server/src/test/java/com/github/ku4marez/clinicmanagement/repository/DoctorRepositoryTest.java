package com.github.ku4marez.clinicmanagement.repository;

import com.github.ku4marez.clinicmanagement.constant.TestConstants;
import com.github.ku4marez.clinicmanagement.entity.DoctorEntity;
import com.github.ku4marez.clinicmanagement.util.CreateEntityUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataMongoTest
@ActiveProfiles("test")
class DoctorRepositoryTest {

    @Autowired
    private DoctorRepository doctorRepository;

    @BeforeEach
    void setUp() {
        doctorRepository.deleteAll();
    }

    @Test
    void testSaveAndFindById() {
        DoctorEntity doctor = CreateEntityUtil.createDefaultDoctorEntity();
        doctorRepository.save(doctor);

        Optional<DoctorEntity> result = doctorRepository.findById(TestConstants.DOCTOR_ID);

        assertTrue(result.isPresent());
        assertEquals(TestConstants.DOCTOR_FIRST_NAME, result.get().getFirstName());
    }

    @Test
    void testSearchByName_Success() {
        DoctorEntity doctor1 = CreateEntityUtil.createDoctorEntity("1", "Alice", "Green", "alice.green@example.com", "9876543210", "Neurology", "DOC654321");
        DoctorEntity doctor2 = CreateEntityUtil.createDoctorEntity("2", "Alicia", "Brown", "alicia.brown@example.com", "1234567890", "Dermatology", "DOC123789");
        doctorRepository.save(doctor1);
        doctorRepository.save(doctor2);

        Pageable pageable = PageRequest.of(0, 10);
        Page<DoctorEntity> result = doctorRepository.searchByName("Ali", pageable);

        assertEquals(2, result.getTotalElements());
        assertEquals("Alice", result.getContent().get(0).getFirstName());
    }
}

