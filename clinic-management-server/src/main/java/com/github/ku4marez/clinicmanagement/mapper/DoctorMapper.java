package com.github.ku4marez.clinicmanagement.mapper;

import com.github.ku4marez.clinicmanagement.dto.DoctorDTO;
import com.github.ku4marez.clinicmanagement.entity.DoctorEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface DoctorMapper {

    DoctorDTO toDto(DoctorEntity doctorEntity);

    DoctorEntity toEntity(DoctorDTO doctorDTO);

    void updateEntityFromDto(DoctorDTO doctorDTO, @MappingTarget DoctorEntity doctorEntity);

}

