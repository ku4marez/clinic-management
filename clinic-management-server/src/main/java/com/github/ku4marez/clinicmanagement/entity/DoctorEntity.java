package com.github.ku4marez.clinicmanagement.entity;

import com.github.ku4marez.commonlibraries.entity.common.PersistentAuditedEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@Document(collection = "doctors")
public class DoctorEntity extends PersistentAuditedEntity {
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String specialty;
    private String licenseNumber;
}
