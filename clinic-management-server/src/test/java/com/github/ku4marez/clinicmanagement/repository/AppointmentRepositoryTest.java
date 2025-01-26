package com.github.ku4marez.clinicmanagement.repository;

import com.github.ku4marez.clinicmanagement.constant.TestConstants;
import com.github.ku4marez.clinicmanagement.entity.AppointmentEntity;
import com.github.ku4marez.clinicmanagement.util.CreateEntityUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataMongoTest
@ActiveProfiles("test")
class AppointmentRepositoryTest {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @BeforeEach
    void setUp() {
        appointmentRepository.deleteAll();
    }

    @Test
    void testFindOverlappingAppointments_Success() {
        LocalDateTime now = LocalDateTime.now();

        AppointmentEntity appointment1 = CreateEntityUtil.createAppointmentEntity(
                "1", TestConstants.DOCTOR_ID, TestConstants.PATIENT_ID, now.plusHours(1), "Scheduled", "Routine Checkup");
        AppointmentEntity appointment2 = CreateEntityUtil.createAppointmentEntity(
                "2", TestConstants.DOCTOR_ID, "patient2", now.plusHours(2), "Scheduled", "Follow-up");

        appointmentRepository.save(appointment1);
        appointmentRepository.save(appointment2);

        List<AppointmentEntity> overlappingAppointments = appointmentRepository.findOverlappingAppointments(
                TestConstants.DOCTOR_ID, now.plusMinutes(30), now.plusHours(2));

        assertEquals(1, overlappingAppointments.size());
        assertEquals(TestConstants.PATIENT_ID, overlappingAppointments.get(0).getPatientId());
    }

    @Test
    void testFindOverlappingAppointments_NoMatch() {
        LocalDateTime now = LocalDateTime.now();

        AppointmentEntity appointment = CreateEntityUtil.createDefaultAppointmentEntity();
        appointmentRepository.save(appointment);

        List<AppointmentEntity> overlappingAppointments = appointmentRepository.findOverlappingAppointments(
                TestConstants.DOCTOR_ID, now.minusHours(2), now.minusHours(1));

        assertTrue(overlappingAppointments.isEmpty());
    }
}
