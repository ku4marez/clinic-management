package com.github.ku4marez.clinicmanagement.util;

import com.github.ku4marez.clinicmanagement.dto.DoctorDTO;
import com.github.ku4marez.clinicmanagement.dto.PatientDTO;
import com.github.ku4marez.clinicmanagement.dto.UserDTO;
import com.github.ku4marez.clinicmanagement.entity.DoctorEntity;
import com.github.ku4marez.clinicmanagement.entity.PatientEntity;
import com.github.ku4marez.clinicmanagement.entity.UserEntity;
import com.github.ku4marez.clinicmanagement.entity.enums.Role;

import java.time.LocalDate;
import java.util.List;

public class CreateEntityUtil {

    private CreateEntityUtil() {
    }

    // User Constants
    public static final String DEFAULT_ID = "123";
    public static final String DEFAULT_FIRST_NAME = "John";
    public static final String DEFAULT_LAST_NAME = "Doe";
    public static final String DEFAULT_EMAIL = "john.doe@example.com";
    public static final String DEFAULT_PASSWORD = "password";
    public static final Role PATIENT_ROLE = Role.PATIENT;

    public static final String OTHER_FIRST_NAME = "Jane";
    public static final String OTHER_LAST_NAME = "Smith";
    public static final String OTHER_EMAIL = "jane.smith@example.com";
    public static final String OTHER_PASSWORD = "password2";
    public static final Role ADMIN_ROLE = Role.ADMIN;

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

    public static UserEntity createDefaultUserEntity() {
        return createUserEntity(DEFAULT_ID, DEFAULT_FIRST_NAME, DEFAULT_LAST_NAME, DEFAULT_EMAIL, DEFAULT_PASSWORD, PATIENT_ROLE);
    }

    public static UserDTO createDefaultUserDTO() {
        return UserDTO.builder()
                .firstName(DEFAULT_FIRST_NAME)
                .lastName(DEFAULT_LAST_NAME)
                .email(DEFAULT_EMAIL)
                .password(DEFAULT_PASSWORD)
                .role(PATIENT_ROLE)
                .build();
    }

    public static UserDTO createOtherUserDTO() {
        return createUserDTO(OTHER_FIRST_NAME, OTHER_LAST_NAME, OTHER_EMAIL, OTHER_PASSWORD, ADMIN_ROLE);
    }

    public static UserEntity createUserEntity(String id, String firstName, String lastName, String email, String password, Role role) {
        UserEntity user = new UserEntity();
        user.setId(id);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPassword(password);
        user.setRole(role);
        return user;
    }

    public static UserDTO createUserDTO(String firstName, String lastName, String email, String password, Role role) {
        return new UserDTO(firstName, lastName, email, password, role);
    }

    public static List<UserEntity> createUserEntityList() {
        return List.of(
                createUserEntity("1", DEFAULT_FIRST_NAME, DEFAULT_LAST_NAME, DEFAULT_EMAIL, DEFAULT_PASSWORD, PATIENT_ROLE),
                createUserEntity("2", OTHER_FIRST_NAME, OTHER_LAST_NAME, OTHER_EMAIL, OTHER_PASSWORD, ADMIN_ROLE)
        );
    }

    public static List<UserDTO> createUserDTOList() {
        return List.of(
                createUserDTO(DEFAULT_FIRST_NAME, DEFAULT_LAST_NAME, DEFAULT_EMAIL, DEFAULT_PASSWORD, PATIENT_ROLE),
                createUserDTO(OTHER_FIRST_NAME, OTHER_LAST_NAME, OTHER_EMAIL, OTHER_PASSWORD, ADMIN_ROLE)
        );
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
}
