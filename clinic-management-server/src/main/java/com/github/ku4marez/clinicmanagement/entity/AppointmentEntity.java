package com.github.ku4marez.clinicmanagement.entity;

import com.github.ku4marez.commonlibraries.entity.common.PersistentAuditedEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@Document(collection = "appointments")
public class AppointmentEntity extends PersistentAuditedEntity {
    private String doctorId;
    private String patientId;
    private LocalDateTime dateTime;
    private String status;
    private String reason;
}