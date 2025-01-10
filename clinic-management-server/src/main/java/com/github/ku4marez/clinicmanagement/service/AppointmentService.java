package com.github.ku4marez.clinicmanagement.service;

import com.github.ku4marez.commonlibraries.dto.AppointmentDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AppointmentService {
    AppointmentDTO createAppointment(AppointmentDTO appointmentDTO);

    AppointmentDTO getAppointmentById(String id);

    Page<AppointmentDTO> getAllAppointments(Pageable pageable);

    List<AppointmentDTO> getAppointmentsByDoctor(String doctorId);

    List<AppointmentDTO> getAppointmentsByPatient(String patientId);

    void deleteAppointment(String id);
}
