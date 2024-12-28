package com.github.ku4marez.clinicmanagement.service.impl;

import com.github.ku4marez.clinicmanagement.dto.PatientDTO;
import com.github.ku4marez.clinicmanagement.entity.PatientEntity;
import com.github.ku4marez.clinicmanagement.exception.PatientNotFoundException;
import com.github.ku4marez.clinicmanagement.mapper.PatientMapper;
import com.github.ku4marez.clinicmanagement.repository.PatientRepository;
import com.github.ku4marez.clinicmanagement.service.PatientService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;
    private final PatientMapper modelMapper;

    public PatientServiceImpl(PatientRepository patientRepository,
                              PatientMapper modelMapper) {
        this.patientRepository = patientRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    @Cacheable(value = "patients", key = "'search_' + #name + '_' + #pageable.pageNumber")
    public Page<PatientDTO> searchPatient(String name, Pageable pageable) {
        Page<PatientEntity> result = patientRepository.searchByName(name, pageable);
        return result.map(modelMapper::toDto);
    }

    @Override
    @CacheEvict(value = "patients", allEntries = true)
    public PatientDTO createPatient(PatientDTO patientDTO) {
        PatientEntity entity = modelMapper.toEntity(patientDTO);
        PatientEntity saved = patientRepository.save(entity);
        return modelMapper.toDto(saved);
    }

    @Override
    @Cacheable(value = "patients", key = "'record_' + #recordNumber")
    public Optional<PatientDTO> getPatientByRecordNumber(String recordNumber) {
        return patientRepository.findByMedicalRecordNumber(recordNumber)
                .map(modelMapper::toDto);
    }

    @Override
    @Cacheable(value = "patients", key = "'all_' + #pageable.pageNumber")
    public Page<PatientDTO> getAllPatients(Pageable pageable) {
        return patientRepository.findAll(pageable).map(modelMapper::toDto);
    }

    @Override
    @CachePut(value = "patients", key = "'patient_' + #id")
    public PatientDTO updatePatient(String id, PatientDTO patientDTO) {
        PatientEntity existingPatient = patientRepository.findById(id)
                .orElseThrow(() -> new PatientNotFoundException(id));

        modelMapper.updateEntityFromDto(patientDTO, existingPatient);

        PatientEntity updatedPatient = patientRepository.save(existingPatient);

        return modelMapper.toDto(updatedPatient);
    }

    @Override
    @CacheEvict(value = "patients", key = "'patient_' + #id")
    public void deletePatient(String id) {
        patientRepository.deleteById(id);
    }
}
