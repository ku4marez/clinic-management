package com.github.ku4marez.clinicmanagement.service.impl;

import com.github.ku4marez.clinicmanagement.dto.AppointmentDTO;
import com.github.ku4marez.clinicmanagement.entity.AppointmentEntity;

import com.github.ku4marez.clinicmanagement.exception.AppointmentNotFoundException;
import com.github.ku4marez.clinicmanagement.exception.AppointmentUnexpectedException;
import com.github.ku4marez.clinicmanagement.repository.AppointmentRepository;
import com.github.ku4marez.clinicmanagement.service.AppointmentService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final ModelMapper modelMapper;

    public AppointmentServiceImpl(AppointmentRepository appointmentRepository, ModelMapper modelMapper) {
        this.appointmentRepository = appointmentRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public AppointmentDTO createAppointment(AppointmentDTO appointmentDTO) {
        validateAppointmentDate(appointmentDTO.dateTime());
        checkForOverlappingAppointments(appointmentDTO);

        AppointmentEntity appointmentEntity = modelMapper.map(appointmentDTO, AppointmentEntity.class);
        AppointmentEntity savedAppointment = appointmentRepository.save(appointmentEntity);

        return modelMapper.map(savedAppointment, AppointmentDTO.class);
    }

    @Override
    public AppointmentDTO getAppointmentById(String id) {
        return appointmentRepository.findById(id)
                .map(appointment -> modelMapper.map(appointment, AppointmentDTO.class))
                .orElseThrow(() -> new AppointmentNotFoundException(id));
    }

    @Override
    public List<AppointmentDTO> getAppointmentsByDoctor(String doctorId) {
        List<AppointmentEntity> appointments = appointmentRepository.findAll()
                .stream()
                .filter(appointment -> doctorId.equals(appointment.getDoctorId()))
                .toList();
        return appointments.stream()
                .map(appointment -> modelMapper.map(appointment, AppointmentDTO.class))
                .toList();
    }

    @Override
    public List<AppointmentDTO> getAppointmentsByPatient(String patientId) {
        List<AppointmentEntity> appointments = appointmentRepository.findAll()
                .stream()
                .filter(appointment -> patientId.equals(appointment.getPatientId()))
                .toList();
        return appointments.stream()
                .map(appointment -> modelMapper.map(appointment, AppointmentDTO.class))
                .toList();
    }

    @Override
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
