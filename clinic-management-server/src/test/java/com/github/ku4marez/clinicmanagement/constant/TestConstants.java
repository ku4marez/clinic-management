package com.github.ku4marez.clinicmanagement.constant;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class TestConstants {
    public static final String USERNAME = "testuser";
    public static final String PASSWORD = "testpassword";
    public static final String EMAIL = "test@example.com";
    public static final String USER_ID = "123";

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
}
