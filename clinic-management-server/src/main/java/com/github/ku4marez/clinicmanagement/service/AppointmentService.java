package com.github.ku4marez.clinicmanagement.service;

import com.github.ku4marez.clinicmanagement.dto.AppointmentDTO;

import java.util.List;

public interface AppointmentService {
    AppointmentDTO createAppointment(AppointmentDTO appointmentDTO);

    AppointmentDTO getAppointmentById(String id);

    List<AppointmentDTO> getAppointmentsByDoctor(String doctorId);

    List<AppointmentDTO> getAppointmentsByPatient(String patientId);

    void deleteAppointment(String id);
}
