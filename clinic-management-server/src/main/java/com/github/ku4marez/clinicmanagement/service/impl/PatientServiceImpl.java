package com.github.ku4marez.clinicmanagement.service.impl;

import com.github.ku4marez.clinicmanagement.dto.PatientDTO;
import com.github.ku4marez.clinicmanagement.entity.PatientEntity;
import com.github.ku4marez.clinicmanagement.exception.PatientNotFoundException;
import com.github.ku4marez.clinicmanagement.mapper.PatientMapper;
import com.github.ku4marez.clinicmanagement.repository.PatientRepository;
import com.github.ku4marez.clinicmanagement.service.PatientService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;
    private final PatientMapper modelMapper;

    public PatientServiceImpl(PatientRepository patientRepository, PatientMapper modelMapper) {
        this.patientRepository = patientRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Page<PatientDTO> searchPatient(String name, Pageable pageable) {
        Page<PatientEntity> result = patientRepository.searchByName(name, pageable);
        return result.map(modelMapper::toDto);
    }

    @Override
    public PatientDTO createPatient(PatientDTO patientDTO) {
        PatientEntity entity = modelMapper.toEntity(patientDTO);
        PatientEntity saved = patientRepository.save(entity);
        return modelMapper.toDto(saved);
    }

    @Override
    public Optional<PatientDTO> getPatientByRecordNumber(String recordNumber) {
        return patientRepository.findByMedicalRecordNumber(recordNumber)
                .map(modelMapper::toDto);
    }

    @Override
    public Page<PatientDTO> getAllPatients(Pageable pageable) {
        return patientRepository.findAll(pageable).map(modelMapper::toDto);
    }

    @Override
    public PatientDTO updatePatient(String id, PatientDTO patientDTO) {
        PatientEntity existingPatient = patientRepository.findById(id)
                .orElseThrow(() -> new PatientNotFoundException(id));

        modelMapper.updateEntityFromDto(patientDTO, existingPatient);

        PatientEntity updatedPatient = patientRepository.save(existingPatient);

        return modelMapper.toDto(updatedPatient);
    }

    @Override
    public void deletePatient(String id) {
        patientRepository.deleteById(id);
    }

}
