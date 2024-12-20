package com.github.ku4marez.clinicmanagement.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@Setter
@Document(collection = "appointments")
public class AppointmentEntity {
    @Id
    private String id;
    private String doctorId;
    private String patientId;
    private LocalDateTime dateTime;
    private String status;
    private String reason;
}