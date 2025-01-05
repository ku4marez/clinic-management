package com.github.ku4marez.clinicmanagement.controller;

import com.github.ku4marez.clinicmanagement.dto.DoctorDTO;
import com.github.ku4marez.clinicmanagement.service.DoctorService;
import com.github.ku4marez.clinicmanagement.util.CreateEntityUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class DoctorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DoctorService doctorService;

    private DoctorDTO doctorDTO;

    @BeforeEach
    void setup() {
        doctorDTO = CreateEntityUtil.createDefaultDoctorDTO();
    }

    @Test
    void testCreateDoctor() throws Exception {
        when(doctorService.createDoctor(any(DoctorDTO.class))).thenReturn(doctorDTO);

        mockMvc.perform(post("/doctors")
                        .with(SecurityMockMvcRequestPostProcessors.user("admin").roles("ADMIN"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {
                                "firstName": "John",
                                "lastName": "Smith",
                                "email": "john.smith@example.com",
                                "phoneNumber": "1234567890",
                                "specialty": "Cardiology",
                                "licenseNumber": "DOC123456"
                            }
                        """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value(CreateEntityUtil.DOCTOR_FIRST_NAME));
    }

    @Test
    void testGetDoctorByEmail() throws Exception {
        when(doctorService.findDoctorByEmailCaseSensitive(CreateEntityUtil.DOCTOR_EMAIL)).thenReturn(doctorDTO);

        mockMvc.perform(get("/doctors/{email}", CreateEntityUtil.DOCTOR_EMAIL)
                        .with(SecurityMockMvcRequestPostProcessors.user("doctor").roles("DOCTOR")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value(CreateEntityUtil.DOCTOR_EMAIL));
    }

    @Test
    void testDeleteDoctor() throws Exception {
        doNothing().when(doctorService).deleteDoctor(CreateEntityUtil.DOCTOR_ID);

        mockMvc.perform(delete("/doctors/{id}", CreateEntityUtil.DOCTOR_ID)
                        .with(SecurityMockMvcRequestPostProcessors.user("admin").roles("ADMIN")))
                .andExpect(status().isNoContent());
    }
}

