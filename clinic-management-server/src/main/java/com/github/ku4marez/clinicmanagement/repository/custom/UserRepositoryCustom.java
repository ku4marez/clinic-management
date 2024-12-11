package com.github.ku4marez.clinicmanagement.repository.custom;

import com.github.ku4marez.clinicmanagement.entity.UserEntity;

import java.util.Optional;

public interface UserRepositoryCustom {
    Optional<UserEntity> findByEmailCaseInsensitive(String email);
}
