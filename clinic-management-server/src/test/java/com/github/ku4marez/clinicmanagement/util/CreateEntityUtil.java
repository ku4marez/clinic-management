package com.github.ku4marez.clinicmanagement.util;

import com.github.ku4marez.clinicmanagement.entity.AppointmentEntity;
import com.github.ku4marez.clinicmanagement.entity.DoctorEntity;
import com.github.ku4marez.clinicmanagement.entity.PatientEntity;
import com.github.ku4marez.commonlibraries.dto.AppointmentDTO;
import com.github.ku4marez.commonlibraries.dto.DoctorDTO;
import com.github.ku4marez.commonlibraries.dto.PatientDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static com.github.ku4marez.clinicmanagement.constant.TestConstants.*;

public class CreateEntityUtil {

    private CreateEntityUtil() {
    }

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
