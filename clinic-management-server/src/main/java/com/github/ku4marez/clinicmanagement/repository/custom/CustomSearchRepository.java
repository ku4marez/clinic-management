package com.github.ku4marez.clinicmanagement.repository.custom;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface CustomSearchRepository<T> {
    Optional<T> findByEmailCaseInsensitive(String email);
    Page<T> searchByName(String name, Pageable pageable);
}
