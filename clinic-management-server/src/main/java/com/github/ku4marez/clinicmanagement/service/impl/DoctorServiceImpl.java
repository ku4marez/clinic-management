package com.github.ku4marez.clinicmanagement.service.impl;

import com.github.ku4marez.clinicmanagement.entity.DoctorEntity;
import com.github.ku4marez.clinicmanagement.exception.DoctorNotFoundException;
import com.github.ku4marez.clinicmanagement.mapper.DoctorMapper;
import com.github.ku4marez.clinicmanagement.repository.DoctorRepository;
import com.github.ku4marez.clinicmanagement.service.DoctorService;
import com.github.ku4marez.commonlibraries.dto.DoctorDTO;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;
    private final DoctorMapper modelMapper;

    public DoctorServiceImpl(DoctorRepository doctorRepository, DoctorMapper modelMapper) {
        this.doctorRepository = doctorRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    @Cacheable(value = "doctors", key = "'search_' + #name + '_' + #pageable.pageNumber + '_' + #pageable.pageSize")
    public Page<DoctorDTO> searchDoctor(String name, Pageable pageable) {
        Page<DoctorEntity> result = doctorRepository.searchByName(name, pageable);
        return result.map(modelMapper::toDto);
    }

    @Override
    @CacheEvict(value = "doctors", allEntries = true)
    public DoctorDTO createDoctor(DoctorDTO doctorDTO) {
        DoctorEntity entity = modelMapper.toEntity(doctorDTO);
        DoctorEntity saved = doctorRepository.save(entity);
        return modelMapper.toDto(saved);
    }

    @Override
    @Cacheable(value = "doctors", key = "'all_' + #pageable.pageNumber + '_' + #pageable.pageSize")
    public Page<DoctorDTO> getAllDoctors(Pageable pageable) {
        return doctorRepository.findAll(pageable).map(modelMapper::toDto);
    }

    @Override
    @Cacheable(value = "doctors", key = "'doctor_email_' + #email")
    public DoctorDTO findDoctorByEmailCaseSensitive(String email) {
        return doctorRepository.findByEmailCaseInsensitive(email)
                .map(modelMapper::toDto)
                .orElseThrow(() -> new DoctorNotFoundException(email));
    }

    @Override
    @CachePut(value = "doctors", key = "'doctor_' + #id")
    public DoctorDTO updateDoctor(String id, DoctorDTO doctorDTO) {
        DoctorEntity existingDoctor = doctorRepository.findById(id)
                .orElseThrow(() -> new DoctorNotFoundException(id));

        modelMapper.updateEntityFromDto(doctorDTO, existingDoctor);

        DoctorEntity updatedDoctor = doctorRepository.save(existingDoctor);
        return modelMapper.toDto(updatedDoctor);
    }

    @Override
    @CacheEvict(value = "doctors", key = "'doctor_' + #id")
    public void deleteDoctor(String id) {
        doctorRepository.deleteById(id);
    }
}
