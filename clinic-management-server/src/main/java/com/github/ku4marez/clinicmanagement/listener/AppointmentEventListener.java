package com.github.ku4marez.clinicmanagement.listener;

import com.github.ku4marez.clinicmanagement.entity.AppointmentEntity;
import com.github.ku4marez.clinicmanagement.event.AppointmentCreatedEvent;
import com.github.ku4marez.clinicmanagement.event.AppointmentUpdatedEvent;
import com.github.ku4marez.commonlibraries.dto.KafkaEvent;
import com.github.ku4marez.commonlibraries.util.KafkaProducerUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AppointmentEventListener {

    private final KafkaProducerUtil kafkaProducerUtil;

    public AppointmentEventListener(KafkaProducerUtil kafkaProducerUtil) {
        this.kafkaProducerUtil = kafkaProducerUtil;
    }

    @EventListener
    public void handleAppointmentCreated(AppointmentCreatedEvent event) {
        AppointmentEntity appointment = event.getAppointment();
        log.info("New appointment created: {} {}", appointment.getPatientId(), appointment.getDoctorId());
        publishAppointmentCreatedEvent(appointment.getId(), appointment.getPatientId());
    }

    @EventListener
    public void handleAppointmentUpdated(AppointmentUpdatedEvent event) {
        AppointmentEntity appointment = event.getAppointment();
        log.info("Appointment updated: {} {}", appointment.getPatientId(), appointment.getDoctorId());
        publishAppointmentUpdatedEvent(appointment.getId(), appointment.getPatientId());
    }

    public void publishAppointmentCreatedEvent(String appointmentId, String patientId) {
        KafkaEvent<String> event = new KafkaEvent<>("APPOINTMENT_CREATED", appointmentId);
        kafkaProducerUtil.sendMessage("appointment.created", patientId, event.toJson());
    }

    public void publishAppointmentUpdatedEvent(String appointmentId, String patientId) {
        KafkaEvent<String> event = new KafkaEvent<>("APPOINTMENT_UPDATED", appointmentId);
        kafkaProducerUtil.sendMessage("appointment.updated", patientId, event.toJson());
    }
}
