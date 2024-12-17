package com.github.ku4marez.clinicmanagement.service.impl;

import com.github.ku4marez.clinicmanagement.dto.DoctorDTO;
import com.github.ku4marez.clinicmanagement.entity.DoctorEntity;
import com.github.ku4marez.clinicmanagement.exception.DoctorNotFoundException;
import com.github.ku4marez.clinicmanagement.repository.DoctorRepository;
import com.github.ku4marez.clinicmanagement.service.DoctorService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;
    private final ModelMapper modelMapper;

    public DoctorServiceImpl(DoctorRepository doctorRepository, ModelMapper modelMapper) {
        this.doctorRepository = doctorRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Page<DoctorDTO> searchDoctor(String name, Pageable pageable) {
        Page<DoctorEntity> result = doctorRepository.searchByName(name, pageable);
        return result.map(this::toDto);
    }

    @Override
    public DoctorDTO createDoctor(DoctorDTO doctorDTO) {
        DoctorEntity entity = fromDto(doctorDTO);
        DoctorEntity saved = doctorRepository.save(entity);
        return toDto(saved);
    }

    @Override
    public Page<DoctorDTO> getAllDoctors(Pageable pageable) {
        return doctorRepository.findAll(pageable).map(this::toDto);
    }

    @Override
    public DoctorDTO findDoctorByEmailCaseSensitive(String email) {
        return doctorRepository.findByEmailCaseInsensitive(email)
                .map(this::toDto)
                .orElseThrow(() -> new DoctorNotFoundException(email));
    }

    @Override
    public DoctorDTO updateDoctor(String id, DoctorDTO doctorDTO) {
        DoctorEntity existingDoctor = doctorRepository.findById(id)
                .orElseThrow(() -> new DoctorNotFoundException(id));

        modelMapper.map(doctorDTO, existingDoctor);

        DoctorEntity updatedDoctor = doctorRepository.save(existingDoctor);
        return modelMapper.map(updatedDoctor, DoctorDTO.class);
    }

    @Override
    public void deleteDoctor(String id) {
        doctorRepository.deleteById(id);
    }

    private DoctorDTO toDto(DoctorEntity entity) {
        return modelMapper.map(entity, DoctorDTO.class);
    }

    private DoctorEntity fromDto(DoctorDTO dto) {
        return modelMapper.map(dto, DoctorEntity.class);
    }
}
