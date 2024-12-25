package com.github.ku4marez.clinicmanagement.mapper;

import com.github.ku4marez.clinicmanagement.dto.AppointmentDTO;
import com.github.ku4marez.clinicmanagement.entity.AppointmentEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface AppointmentMapper {

    AppointmentDTO toDto(AppointmentEntity appointmentEntity);

    AppointmentEntity toEntity(AppointmentDTO appointmentDTO);

    void updateEntityFromDto(AppointmentDTO appointmentDTO, @MappingTarget AppointmentEntity appointmentEntity);

}

