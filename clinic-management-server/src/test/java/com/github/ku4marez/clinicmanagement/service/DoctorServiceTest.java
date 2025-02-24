package com.github.ku4marez.clinicmanagement.service;

import com.github.ku4marez.clinicmanagement.constant.TestConstants;
import com.github.ku4marez.clinicmanagement.entity.DoctorEntity;
import com.github.ku4marez.clinicmanagement.mapper.DoctorMapper;
import com.github.ku4marez.clinicmanagement.repository.DoctorRepository;
import com.github.ku4marez.clinicmanagement.service.impl.DoctorServiceImpl;
import com.github.ku4marez.clinicmanagement.util.CreateEntityUtil;
import com.github.ku4marez.commonlibraries.dto.DoctorDTO;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DoctorServiceTest {

    @Mock
    private DoctorRepository doctorRepository;

    @Mock
    private DoctorMapper modelMapper;

    @InjectMocks
    private DoctorServiceImpl doctorService;

    @Test
    void testCreateDoctor() {
        DoctorDTO doctorDTO = CreateEntityUtil.createDefaultDoctorDTO();
        DoctorEntity doctorEntity = CreateEntityUtil.createDefaultDoctorEntity();

        when(modelMapper.toEntity(doctorDTO)).thenReturn(doctorEntity);
        when(doctorRepository.save(doctorEntity)).thenReturn(doctorEntity);
        when(modelMapper.toDto(doctorEntity)).thenReturn(doctorDTO);

        DoctorDTO result = doctorService.createDoctor(doctorDTO);

        assertEquals(TestConstants.DOCTOR_FIRST_NAME, result.firstName());
        verify(doctorRepository, times(1)).save(doctorEntity);
    }

    @Test
    void testSearchDoctor() {
        Pageable pageable = PageRequest.of(0, 2);
        List<DoctorEntity> doctors = List.of(CreateEntityUtil.createDefaultDoctorEntity());
        Page<DoctorEntity> doctorPage = new PageImpl<>(doctors, pageable, doctors.size());
        DoctorDTO doctorDTO = CreateEntityUtil.createDefaultDoctorDTO();

        when(doctorRepository.searchByName("John", pageable)).thenReturn(doctorPage);
        when(modelMapper.toDto(any(DoctorEntity.class))).thenReturn(doctorDTO);

        Page<DoctorDTO> result = doctorService.searchDoctor("John", pageable);

        assertEquals(1, result.getTotalElements());
        assertEquals(TestConstants.DOCTOR_FIRST_NAME, result.getContent().get(0).firstName());
        verify(doctorRepository, times(1)).searchByName("John", pageable);
        verify(modelMapper, times(1)).toDto(any(DoctorEntity.class));
    }

    @Test
    void testFindDoctorByEmailCaseSensitive() {
        DoctorEntity doctorEntity = CreateEntityUtil.createDefaultDoctorEntity();
        DoctorDTO doctorDTO = CreateEntityUtil.createDefaultDoctorDTO();

        when(doctorRepository.findByEmailCaseInsensitive(TestConstants.DOCTOR_EMAIL))
                .thenReturn(Optional.of(doctorEntity));
        when(modelMapper.toDto(doctorEntity)).thenReturn(doctorDTO);

        DoctorDTO result = doctorService.findDoctorByEmailCaseSensitive(TestConstants.DOCTOR_EMAIL);

        assertEquals(TestConstants.DOCTOR_FIRST_NAME, result.firstName());
        verify(doctorRepository, times(1)).findByEmailCaseInsensitive(TestConstants.DOCTOR_EMAIL);
    }
}
