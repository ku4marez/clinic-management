package com.github.ku4marez.clinicmanagement.listener;

import com.github.ku4marez.clinicmanagement.entity.AppointmentEntity;
import com.github.ku4marez.clinicmanagement.event.AppointmentCreatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AppointmentEventListener {

    @EventListener
    public void handleAppointmentCreated(AppointmentCreatedEvent event) {
        AppointmentEntity appointment = event.getAppointment();
        log.info("New appointment created: {} {}", appointment.getPatientId(), appointment.getDoctorId());
    }
}