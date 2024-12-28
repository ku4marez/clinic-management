package com.github.ku4marez.clinicmanagement.service.impl;

import com.github.ku4marez.clinicmanagement.dto.AppointmentDTO;
import com.github.ku4marez.clinicmanagement.entity.AppointmentEntity;

import com.github.ku4marez.clinicmanagement.event.AppointmentCreatedEvent;
import com.github.ku4marez.clinicmanagement.exception.AppointmentNotFoundException;
import com.github.ku4marez.clinicmanagement.exception.AppointmentUnexpectedException;
import com.github.ku4marez.clinicmanagement.mapper.AppointmentMapper;
import com.github.ku4marez.clinicmanagement.repository.AppointmentRepository;
import com.github.ku4marez.clinicmanagement.service.AppointmentService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    private final ApplicationEventPublisher eventPublisher;
    private final AppointmentRepository appointmentRepository;
    private final AppointmentMapper modelMapper;

    public AppointmentServiceImpl(ApplicationEventPublisher eventPublisher,
                                  AppointmentRepository appointmentRepository,
                                  AppointmentMapper modelMapper) {
        this.eventPublisher = eventPublisher;
        this.appointmentRepository = appointmentRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    @CacheEvict(value = "appointments", allEntries = true)
    public AppointmentDTO createAppointment(AppointmentDTO appointmentDTO) {
        validateAppointmentDate(appointmentDTO.dateTime());
        checkForOverlappingAppointments(appointmentDTO);

        AppointmentEntity appointmentEntity = modelMapper.toEntity(appointmentDTO);
        AppointmentEntity savedAppointment = appointmentRepository.save(appointmentEntity);
        eventPublisher.publishEvent(new AppointmentCreatedEvent(this, savedAppointment));

        return modelMapper.toDto(savedAppointment);
    }

    @Override
    @Cacheable(value = "appointments", key = "'appointment_' + #id")
    public AppointmentDTO getAppointmentById(String id) {
        return appointmentRepository.findById(id)
                .map(modelMapper::toDto)
                .orElseThrow(() -> new AppointmentNotFoundException(id));
    }

    @Override
    @Cacheable(value = "appointments", key = "'appointments_page_' + #pageable.pageNumber + '_' + #pageable.pageSize")
    public Page<AppointmentDTO> getAllAppointments(Pageable pageable) {
        return appointmentRepository.findAll(pageable).map(modelMapper::toDto);
    }

    @Override
    @Cacheable(value = "appointments", key = "'appointments_doctor_' + #doctorId")
    public List<AppointmentDTO> getAppointmentsByDoctor(String doctorId) {
        List<AppointmentEntity> appointments = appointmentRepository.findAll()
                .stream()
                .filter(appointment -> doctorId.equals(appointment.getDoctorId()))
                .toList();
        return appointments.stream()
                .map(modelMapper::toDto)
                .toList();
    }

    @Override
    @Cacheable(value = "appointments", key = "'appointments_patient_' + #patientId")
    public List<AppointmentDTO> getAppointmentsByPatient(String patientId) {
        List<AppointmentEntity> appointments = appointmentRepository.findAll()
                .stream()
                .filter(appointment -> patientId.equals(appointment.getPatientId()))
                .toList();
        return appointments.stream()
                .map(modelMapper::toDto)
                .toList();
    }

    @Override
    @CacheEvict(value = "appointments", key = "'appointment_' + #id")
    public void deleteAppointment(String id) {
        appointmentRepository.deleteById(id);
    }

    private void validateAppointmentDate(LocalDateTime dateTime) {
        if (dateTime.isBefore(LocalDateTime.now())) {
            throw new AppointmentUnexpectedException("Appointment date must be in the future: ", dateTime);
        }
    }

    private void checkForOverlappingAppointments(AppointmentDTO appointmentDTO) {
        LocalDateTime startTime = appointmentDTO.dateTime();
        LocalDateTime endTime = startTime.plusMinutes(30);

        List<AppointmentEntity> overlappingAppointments = appointmentRepository.findOverlappingAppointments(
                appointmentDTO.doctorId(), startTime, endTime);

        if (!overlappingAppointments.isEmpty()) {
            throw new AppointmentUnexpectedException("Overlapping appointment exists for the given time slot: ", startTime);
        }
    }
}
