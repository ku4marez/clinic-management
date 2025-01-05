package com.github.ku4marez.clinicmanagement.controller;

import com.github.ku4marez.clinicmanagement.dto.AppointmentDTO;
import com.github.ku4marez.clinicmanagement.service.AppointmentService;
import com.github.ku4marez.clinicmanagement.util.CreateEntityUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.hasSize;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class AppointmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AppointmentService appointmentService;

    private AppointmentDTO appointmentDTO;

    @BeforeEach
    void setup() {
        appointmentDTO = CreateEntityUtil.createDefaultAppointmentDTO();
    }

    @Test
    void testCreateAppointment() throws Exception {
        when(appointmentService.createAppointment(any(AppointmentDTO.class))).thenReturn(appointmentDTO);

        mockMvc.perform(post("/appointments")
                        .with(SecurityMockMvcRequestPostProcessors.user("admin").roles("ADMIN"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                    {
                                        "doctorId": "1",
                                        "patientId": "2",
                                        "dateTime": "2025-01-06T10:00:00",
                                        "status": "Scheduled",
                                        "reason": "Routine Checkup"
                                    }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.reason").value(CreateEntityUtil.APPOINTMENT_REASON));
    }

    @Test
    void testGetAppointmentById() throws Exception {
        when(appointmentService.getAppointmentById(CreateEntityUtil.APPOINTMENT_ID)).thenReturn(appointmentDTO);

        mockMvc.perform(get("/appointments/{id}", CreateEntityUtil.APPOINTMENT_ID)
                        .with(SecurityMockMvcRequestPostProcessors.user("doctor").roles("DOCTOR")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.reason").value(CreateEntityUtil.APPOINTMENT_REASON));
    }

    @Test
    void testGetAllAppointments() throws Exception {
        Page<AppointmentDTO> appointments = new PageImpl<>(List.of(appointmentDTO));
        when(appointmentService.getAllAppointments(any(Pageable.class))).thenReturn(appointments);

        mockMvc.perform(get("/appointments")
                        .param("page", "0")
                        .param("size", "10")
                        .with(SecurityMockMvcRequestPostProcessors.user("doctor").roles("DOCTOR")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content[0].reason").value(CreateEntityUtil.APPOINTMENT_REASON));
    }

    @Test
    void testDeleteAppointment() throws Exception {
        doNothing().when(appointmentService).deleteAppointment(CreateEntityUtil.APPOINTMENT_ID);

        mockMvc.perform(delete("/appointments/{id}", CreateEntityUtil.APPOINTMENT_ID)
                        .with(SecurityMockMvcRequestPostProcessors.user("admin").roles("ADMIN")))
                .andExpect(status().isNoContent());
    }
}

