package com.github.ku4marez.clinicmanagement.service;

import com.github.ku4marez.clinicmanagement.entity.AppointmentEntity;
import com.github.ku4marez.clinicmanagement.event.AppointmentCreatedEvent;
import com.github.ku4marez.clinicmanagement.mapper.AppointmentMapper;
import com.github.ku4marez.clinicmanagement.repository.AppointmentRepository;
import com.github.ku4marez.clinicmanagement.service.impl.AppointmentServiceImpl;
import com.github.ku4marez.clinicmanagement.util.CreateEntityUtil;
import com.github.ku4marez.commonlibraries.dto.AppointmentDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AppointmentServiceTest {

    @Mock
    private AppointmentRepository appointmentRepository;

    @Mock
    private AppointmentMapper modelMapper;

    @Mock
    private ApplicationEventPublisher eventPublisher;

    @InjectMocks
    private AppointmentServiceImpl appointmentService;

    @Test
    void testCreateAppointment() {
        AppointmentDTO appointmentDTO = CreateEntityUtil.createDefaultAppointmentDTO();
        AppointmentEntity appointmentEntity = CreateEntityUtil.createDefaultAppointmentEntity();

        when(modelMapper.toEntity(appointmentDTO)).thenReturn(appointmentEntity);
        when(appointmentRepository.save(appointmentEntity)).thenReturn(appointmentEntity);
        when(modelMapper.toDto(appointmentEntity)).thenReturn(appointmentDTO);

        AppointmentDTO result = appointmentService.createAppointment(appointmentDTO);

        assertEquals(CreateEntityUtil.APPOINTMENT_REASON, result.reason());
        verify(appointmentRepository, times(1)).save(appointmentEntity);
        verify(eventPublisher, times(1)).publishEvent(any(AppointmentCreatedEvent.class));
    }

    @Test
    void testGetAppointmentById() {
        AppointmentEntity appointmentEntity = CreateEntityUtil.createDefaultAppointmentEntity();
        AppointmentDTO appointmentDTO = CreateEntityUtil.createDefaultAppointmentDTO();

        when(appointmentRepository.findById(CreateEntityUtil.APPOINTMENT_ID))
                .thenReturn(Optional.of(appointmentEntity));
        when(modelMapper.toDto(appointmentEntity)).thenReturn(appointmentDTO);

        AppointmentDTO result = appointmentService.getAppointmentById(CreateEntityUtil.APPOINTMENT_ID);

        assertEquals(CreateEntityUtil.APPOINTMENT_REASON, result.reason());
        verify(appointmentRepository, times(1)).findById(CreateEntityUtil.APPOINTMENT_ID);
    }
}

