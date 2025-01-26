package com.github.ku4marez.clinicmanagement.controller;

import com.github.ku4marez.clinicmanagement.constant.TestConstants;
import com.github.ku4marez.clinicmanagement.service.PatientService;
import com.github.ku4marez.clinicmanagement.util.CreateEntityUtil;
import com.github.ku4marez.commonlibraries.dto.PatientDTO;
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

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class PatientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PatientService patientService;

    private PatientDTO patientDTO;

    @BeforeEach
    void setup() {
        patientDTO = CreateEntityUtil.createDefaultPatientDTO();
    }

    @Test
    void testCreatePatient() throws Exception {
        when(patientService.createPatient(any(PatientDTO.class))).thenReturn(patientDTO);

        mockMvc.perform(post("/patients")
                        .with(SecurityMockMvcRequestPostProcessors.user("admin").roles("ADMIN"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {
                                "firstName": "Jane",
                                "lastName": "Doe",
                                "email": "jane.doe@example.com",
                                "dateOfBirth": "1990-01-01",
                                "phoneNumber": "9876543210",
                                "address": "123 Main St",
                                "medicalRecordNumber": "MRN123456"
                            }
                        """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value(TestConstants.PATIENT_FIRST_NAME));
    }

    @Test
    void testGetPatientByRecordNumber() throws Exception {
        when(patientService.getPatientByRecordNumber(TestConstants.PATIENT_RECORD_NUMBER)).thenReturn(Optional.of(patientDTO));

        mockMvc.perform(get("/patients/{recordNumber}", TestConstants.PATIENT_RECORD_NUMBER)
                        .with(SecurityMockMvcRequestPostProcessors.user("doctor").roles("DOCTOR")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.medicalRecordNumber").value(TestConstants.PATIENT_RECORD_NUMBER));
    }

    @Test
    void testDeletePatient() throws Exception {
        doNothing().when(patientService).deletePatient(TestConstants.PATIENT_ID);

        mockMvc.perform(delete("/patients/{id}", TestConstants.PATIENT_ID)
                        .with(SecurityMockMvcRequestPostProcessors.user("admin").roles("ADMIN")))
                .andExpect(status().isNoContent());
    }
}

