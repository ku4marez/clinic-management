package com.github.ku4marez.clinicmanagement.util;

import com.github.ku4marez.clinicmanagement.dto.AppointmentDTO;
import com.github.ku4marez.clinicmanagement.dto.DoctorDTO;
import com.github.ku4marez.clinicmanagement.dto.PatientDTO;
import com.github.ku4marez.clinicmanagement.entity.AppointmentEntity;
import com.github.ku4marez.clinicmanagement.entity.DoctorEntity;
import com.github.ku4marez.clinicmanagement.entity.PatientEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class CreateEntityUtil {

    private CreateEntityUtil() {
    }

    // Doctor Constants
    public static final String DOCTOR_ID = "1";
    public static final String DOCTOR_FIRST_NAME = "John";
    public static final String DOCTOR_LAST_NAME = "Smith";
    public static final String DOCTOR_EMAIL = "john.smith@clinic.com";
    public static final String DOCTOR_PHONE = "123-456-7890";
    public static final String DOCTOR_SPECIALTY = "Cardiology";
    public static final String DOCTOR_LICENSE = "DOC123456";

    // Patient Constants
    public static final String PATIENT_ID = "1";
    public static final String PATIENT_FIRST_NAME = "Jane";
    public static final String PATIENT_LAST_NAME = "Doe";
    public static final String PATIENT_EMAIL = "jane.doe@clinic.com";
    public static final LocalDate PATIENT_DOB = LocalDate.of(1990, 1, 1);
    public static final String PATIENT_PHONE = "987-654-3210";
    public static final String PATIENT_ADDRESS = "123 Main St, Springfield";
    public static final String PATIENT_RECORD_NUMBER = "PAT123456";

    // Appointment Constants
    public static final String APPOINTMENT_ID = "1";
    public static final LocalDateTime APPOINTMENT_DATETIME = LocalDateTime.now().plusHours(1);
    public static final String APPOINTMENT_STATUS = "Scheduled";
    public static final String APPOINTMENT_REASON = "Routine Checkup";

    public static DoctorEntity createDefaultDoctorEntity() {
        return createDoctorEntity(DOCTOR_ID, DOCTOR_FIRST_NAME, DOCTOR_LAST_NAME, DOCTOR_EMAIL, DOCTOR_PHONE, DOCTOR_SPECIALTY, DOCTOR_LICENSE);
    }

    public static DoctorDTO createDefaultDoctorDTO() {
        return DoctorDTO.builder()
                .firstName(DOCTOR_FIRST_NAME)
                .lastName(DOCTOR_LAST_NAME)
                .email(DOCTOR_EMAIL)
                .phoneNumber(DOCTOR_PHONE)
                .specialty(DOCTOR_SPECIALTY)
                .licenseNumber(DOCTOR_LICENSE)
                .build();
    }

    public static DoctorEntity createDoctorEntity(String id, String firstName, String lastName, String email, String phoneNumber, String specialty, String licenseNumber) {
        DoctorEntity doctor = new DoctorEntity();
        doctor.setId(id);
        doctor.setFirstName(firstName);
        doctor.setLastName(lastName);
        doctor.setEmail(email);
        doctor.setPhoneNumber(phoneNumber);
        doctor.setSpecialty(specialty);
        doctor.setLicenseNumber(licenseNumber);
        return doctor;
    }

    public static PatientEntity createDefaultPatientEntity() {
        return createPatientEntity(PATIENT_ID, PATIENT_FIRST_NAME, PATIENT_LAST_NAME, PATIENT_EMAIL, PATIENT_DOB, PATIENT_PHONE, PATIENT_ADDRESS, PATIENT_RECORD_NUMBER);
    }

    public static PatientDTO createDefaultPatientDTO() {
        return PatientDTO.builder()
                .firstName(PATIENT_FIRST_NAME)
                .lastName(PATIENT_LAST_NAME)
                .email(PATIENT_EMAIL)
                .dateOfBirth(PATIENT_DOB)
                .phoneNumber(PATIENT_PHONE)
                .address(PATIENT_ADDRESS)
                .medicalRecordNumber(PATIENT_RECORD_NUMBER)
                .build();
    }

    public static PatientEntity createPatientEntity(String id, String firstName, String lastName, String email, LocalDate dob, String phone, String address, String recordNumber) {
        PatientEntity patient = new PatientEntity();
        patient.setId(id);
        patient.setFirstName(firstName);
        patient.setLastName(lastName);
        patient.setEmail(email);
        patient.setDateOfBirth(dob);
        patient.setPhoneNumber(phone);
        patient.setAddress(address);
        patient.setMedicalRecordNumber(recordNumber);
        return patient;
    }

    public static AppointmentEntity createDefaultAppointmentEntity() {
        return createAppointmentEntity(APPOINTMENT_ID, DOCTOR_ID, PATIENT_ID, APPOINTMENT_DATETIME, APPOINTMENT_STATUS, APPOINTMENT_REASON);
    }

    public static AppointmentEntity createAppointmentEntity(String id, String doctorId, String patientId, LocalDateTime dateTime, String status, String reason) {
        AppointmentEntity appointment = new AppointmentEntity();
        appointment.setId(id);
        appointment.setDoctorId(doctorId);
        appointment.setPatientId(patientId);
        appointment.setDateTime(dateTime);
        appointment.setStatus(status);
        appointment.setReason(reason);
        return appointment;
    }

    public static AppointmentDTO createDefaultAppointmentDTO() {
        return AppointmentDTO.builder()
                .reason(APPOINTMENT_REASON)
                .status(APPOINTMENT_STATUS)
                .dateTime(APPOINTMENT_DATETIME)
                .patientId(PATIENT_ID)
                .doctorId(DOCTOR_ID)
                .build();
    }
}
