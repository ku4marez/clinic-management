package com.github.ku4marez.clinicmanagement.event;

import com.github.ku4marez.clinicmanagement.entity.AppointmentEntity;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class AppointmentUpdatedEvent extends ApplicationEvent {
    private final AppointmentEntity appointment;

    public AppointmentUpdatedEvent(Object source, AppointmentEntity appointment) {
        super(source);
        this.appointment = appointment;
    }
}