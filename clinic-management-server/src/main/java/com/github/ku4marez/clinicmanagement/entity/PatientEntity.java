package com.github.ku4marez.clinicmanagement.entity;

import com.github.ku4marez.clinicmanagement.entity.common.PersistentAuditedEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@Document(collection = "patients")
public class PatientEntity extends PersistentAuditedEntity {
    private String firstName;
    private String lastName;
    private String email;
    private LocalDate dateOfBirth;
    private String phoneNumber;
    private String address;
    private String medicalRecordNumber;
}
