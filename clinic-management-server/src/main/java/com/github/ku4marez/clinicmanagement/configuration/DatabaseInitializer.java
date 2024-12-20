package com.github.ku4marez.clinicmanagement.configuration;

import com.github.ku4marez.clinicmanagement.entity.AppointmentEntity;
import com.github.ku4marez.clinicmanagement.entity.DoctorEntity;
import com.github.ku4marez.clinicmanagement.entity.PatientEntity;
import com.github.ku4marez.clinicmanagement.entity.UserEntity;
import com.github.ku4marez.clinicmanagement.entity.enums.Role;
import jakarta.annotation.PostConstruct;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
public class DatabaseInitializer {

    private final MongoTemplate mongoTemplate;
    private final PasswordEncoder passwordEncoder;

    public DatabaseInitializer(MongoTemplate mongoTemplate, PasswordEncoder passwordEncoder) {
        this.mongoTemplate = mongoTemplate;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void init() {
        initializeUsersCollection();
        initializeDoctorsCollection();
        initializePatientsCollection();
        initializeAppointmentsCollection();
    }

    // =================== USERS COLLECTION ===================
    private void initializeUsersCollection() {
        String collectionName = "users";

        if (!mongoTemplate.collectionExists(collectionName)) {
            mongoTemplate.createCollection(collectionName);
        }
        applyUserValidationRules(collectionName);
        seedDefaultUsers();
    }

    private void applyUserValidationRules(String collectionName) {
        String validationSchema = """
        {
            "$jsonSchema": {
                "bsonType": "object",
                "required": ["firstName", "lastName", "email", "password", "role"],
                "properties": {
                    "firstName": { "bsonType": "string" },
                    "lastName": { "bsonType": "string" },
                    "email": {
                        "bsonType": "string",
                        "pattern": "^.+@.+\\\\..+$"
                    },
                    "password": { "bsonType": "string" },
                    "role": {
                        "bsonType": "string",
                        "enum": ["ADMIN", "DOCTOR", "PATIENT"]
                    }
                }
            }
        }
        """;

        mongoTemplate.executeCommand("""
        {
            "collMod": "%s",
            "validator": %s,
            "validationLevel": "strict",
            "validationAction": "error"
        }
        """.formatted(collectionName, validationSchema));
    }

    private void seedDefaultUsers() {
        if (mongoTemplate.findAll(UserEntity.class).isEmpty()) {
            UserEntity adminUser = new UserEntity();
            adminUser.setFirstName("Admin");
            adminUser.setLastName("User");
            adminUser.setEmail("admin@example.com");
            adminUser.setPassword(passwordEncoder.encode("securePassword"));
            adminUser.setRole(Role.ADMIN);

            mongoTemplate.save(adminUser);
        }
    }

    // =================== DOCTORS COLLECTION ===================
    private void initializeDoctorsCollection() {
        String collectionName = "doctors";

        if (!mongoTemplate.collectionExists(collectionName)) {
            mongoTemplate.createCollection(collectionName);
        }
        applyDoctorValidationRules(collectionName);
        seedDefaultDoctors();
    }

    private void applyDoctorValidationRules(String collectionName) {
        String validationSchema = """
        {
            "$jsonSchema": {
                "bsonType": "object",
                "required": ["firstName", "lastName", "email", "phoneNumber", "specialty", "licenseNumber"],
                "properties": {
                    "firstName": { "bsonType": "string" },
                    "lastName": { "bsonType": "string" },
                    "email": {
                        "bsonType": "string",
                        "pattern": "^.+@.+\\\\..+$"
                    },
                    "phoneNumber": { "bsonType": "string" },
                    "specialty": { "bsonType": "string" },
                    "licenseNumber": { "bsonType": "string" }
                }
            }
        }
        """;

        mongoTemplate.executeCommand("""
        {
            "collMod": "%s",
            "validator": %s,
            "validationLevel": "strict",
            "validationAction": "error"
        }
        """.formatted(collectionName, validationSchema));
    }

    private void seedDefaultDoctors() {
        if (mongoTemplate.findAll(DoctorEntity.class).isEmpty()) {
            DoctorEntity doctor = new DoctorEntity();
            doctor.setId("64a67b12e45c9c2ff91f5a3d");
            doctor.setFirstName("John");
            doctor.setLastName("Smith");
            doctor.setEmail("john.smith@clinic.com");
            doctor.setPhoneNumber("123-456-7890");
            doctor.setSpecialty("Cardiology");
            doctor.setLicenseNumber("DOC123456");

            mongoTemplate.save(doctor);
        }
    }

    // =================== PATIENTS COLLECTION ===================
    private void initializePatientsCollection() {
        String collectionName = "patients";

        if (!mongoTemplate.collectionExists(collectionName)) {
            mongoTemplate.createCollection(collectionName);
        }
        applyPatientValidationRules(collectionName);
        seedDefaultPatients();
    }

    private void applyPatientValidationRules(String collectionName) {
        String validationSchema = """
        {
            "$jsonSchema": {
                "bsonType": "object",
                "required": ["firstName", "lastName", "email", "dateOfBirth", "phoneNumber", "address", "medicalRecordNumber"],
                "properties": {
                    "firstName": { "bsonType": "string" },
                    "lastName": { "bsonType": "string" },
                    "email": {
                        "bsonType": "string",
                        "pattern": "^.+@.+\\\\..+$"
                    },
                    "dateOfBirth": { "bsonType": "date" },
                    "phoneNumber": { "bsonType": "string" },
                    "address": { "bsonType": "string" },
                    "medicalRecordNumber": { "bsonType": "string" }
                }
            }
        }
        """;

        mongoTemplate.executeCommand("""
        {
            "collMod": "%s",
            "validator": %s,
            "validationLevel": "strict",
            "validationAction": "error"
        }
        """.formatted(collectionName, validationSchema));
    }

    private void seedDefaultPatients() {
        if (mongoTemplate.findAll(PatientEntity.class).isEmpty()) {
            PatientEntity patient = new PatientEntity();
            patient.setId("64a67b12e45c9c2ff91f5a3e");
            patient.setFirstName("Jane");
            patient.setLastName("Doe");
            patient.setEmail("jane.doe@clinic.com");
            patient.setDateOfBirth(LocalDate.of(1990, 1, 1));
            patient.setPhoneNumber("987-654-3210");
            patient.setAddress("123 Main St, Springfield");
            patient.setMedicalRecordNumber("PAT123456");

            mongoTemplate.save(patient);
        }
    }

    // =================== APPOINTMENTS COLLECTION ===================
    private void initializeAppointmentsCollection() {
        String collectionName = "appointments";

        if (!mongoTemplate.collectionExists(collectionName)) {
            mongoTemplate.createCollection(collectionName);
        }
        applyAppointmentValidationRules(collectionName);
        seedDefaultAppointments();
    }

    private void applyAppointmentValidationRules(String collectionName) {
        String validationSchema = """
        {
            "$jsonSchema": {
                "bsonType": "object",
                "required": ["doctorId", "patientId", "dateTime", "status", "reason"],
                "properties": {
                    "doctorId": { "bsonType": "string" },
                    "patientId": { "bsonType": "string" },
                    "dateTime": { "bsonType": "date" },
                    "status": {
                        "bsonType": "string",
                        "enum": ["Scheduled", "Completed", "Canceled"]
                    },
                    "reason": { "bsonType": "string" }
                }
            }
        }
        """;

        mongoTemplate.executeCommand("""
        {
            "collMod": "%s",
            "validator": %s,
            "validationLevel": "strict",
            "validationAction": "error"
        }
        """.formatted(collectionName, validationSchema));
    }

    private void seedDefaultAppointments() {
        if (mongoTemplate.findAll(AppointmentEntity.class).isEmpty()) {
            AppointmentEntity appointment = new AppointmentEntity();
            appointment.setDoctorId("64a67b12e45c9c2ff91f5a3d");
            appointment.setPatientId("64a67b12e45c9c2ff91f5a3e");
            appointment.setDateTime(LocalDateTime.now().plusDays(1));
            appointment.setStatus("Scheduled");
            appointment.setReason("Routine Checkup");

            mongoTemplate.save(appointment);
        }
    }
}
