package com.github.ku4marez.clinicmanagement.service;

import com.github.ku4marez.clinicmanagement.dto.PatientDTO;
import com.github.ku4marez.clinicmanagement.entity.PatientEntity;
import com.github.ku4marez.clinicmanagement.mapper.PatientMapper;
import com.github.ku4marez.clinicmanagement.repository.PatientRepository;
import com.github.ku4marez.clinicmanagement.service.impl.PatientServiceImpl;
import com.github.ku4marez.clinicmanagement.util.CreateEntityUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PatientServiceTest {

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private PatientMapper modelMapper;

    @InjectMocks
    private PatientServiceImpl patientService;

    @Test
    void testSearchPatient() {
        Pageable pageable = PageRequest.of(0, 2);
        List<PatientEntity> patients = List.of(CreateEntityUtil.createDefaultPatientEntity());
        Page<PatientEntity> patientPage = new PageImpl<>(patients, pageable, patients.size());
        PatientDTO patientDTO = CreateEntityUtil.createDefaultPatientDTO();

        when(patientRepository.searchByName("Jane", pageable)).thenReturn(patientPage);
        when(modelMapper.toDto(any(PatientEntity.class))).thenReturn(patientDTO);

        Page<PatientDTO> result = patientService.searchPatient("Jane", pageable);

        assertEquals(1, result.getTotalElements());
        assertEquals(CreateEntityUtil.PATIENT_FIRST_NAME, result.getContent().get(0).firstName());
        verify(patientRepository, times(1)).searchByName("Jane", pageable);
        verify(modelMapper, times(1)).toDto(any(PatientEntity.class));
    }

    @Test
    void testCreatePatient() {
        PatientDTO patientDTO = CreateEntityUtil.createDefaultPatientDTO();
        PatientEntity patientEntity = CreateEntityUtil.createDefaultPatientEntity();

        when(modelMapper.toEntity(patientDTO)).thenReturn(patientEntity);
        when(patientRepository.save(patientEntity)).thenReturn(patientEntity);
        when(modelMapper.toDto(patientEntity)).thenReturn(patientDTO);

        PatientDTO result = patientService.createPatient(patientDTO);

        assertEquals(CreateEntityUtil.PATIENT_FIRST_NAME, result.firstName());
        verify(patientRepository, times(1)).save(patientEntity);
    }

    @Test
    void testGetPatientByRecordNumber() {
        PatientEntity patientEntity = CreateEntityUtil.createDefaultPatientEntity();
        PatientDTO patientDTO = CreateEntityUtil.createDefaultPatientDTO();

        when(patientRepository.findByMedicalRecordNumber(CreateEntityUtil.PATIENT_RECORD_NUMBER))
                .thenReturn(Optional.of(patientEntity));
        when(modelMapper.toDto(patientEntity)).thenReturn(patientDTO);

        Optional<PatientDTO> result = patientService.getPatientByRecordNumber(CreateEntityUtil.PATIENT_RECORD_NUMBER);

        assertTrue(result.isPresent());
        assertEquals(CreateEntityUtil.PATIENT_FIRST_NAME, result.get().firstName());
        verify(patientRepository, times(1)).findByMedicalRecordNumber(CreateEntityUtil.PATIENT_RECORD_NUMBER);
    }

    @Test
    void testDeletePatient() {
        patientService.deletePatient(CreateEntityUtil.PATIENT_ID);

        verify(patientRepository, times(1)).deleteById(CreateEntityUtil.PATIENT_ID);
    }
}

