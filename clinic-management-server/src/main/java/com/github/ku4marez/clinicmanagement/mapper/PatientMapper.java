package com.github.ku4marez.clinicmanagement.mapper;

import com.github.ku4marez.clinicmanagement.entity.PatientEntity;
import com.github.ku4marez.commonlibraries.dto.PatientDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PatientMapper {

    PatientDTO toDto(PatientEntity patientEntity);

    PatientEntity toEntity(PatientDTO patientDTO);

    void updateEntityFromDto(PatientDTO patientDTO, @MappingTarget PatientEntity patientEntity);
}
