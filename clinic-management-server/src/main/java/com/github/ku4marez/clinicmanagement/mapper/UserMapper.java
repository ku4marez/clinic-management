package com.github.ku4marez.clinicmanagement.mapper;

import com.github.ku4marez.clinicmanagement.dto.UserDTO;
import com.github.ku4marez.clinicmanagement.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDTO toDto(UserEntity userEntity);

    UserEntity toEntity(UserDTO userDTO);

    void updateEntityFromDto(UserDTO userDTO, @MappingTarget UserEntity userEntity);

}
