package com.github.ku4marez.clinicmanagement.repository;

import com.github.ku4marez.clinicmanagement.entity.PatientEntity;
import com.github.ku4marez.clinicmanagement.repository.custom.CustomSearchRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PatientRepository extends MongoRepository<PatientEntity, String>, CustomSearchRepository<PatientEntity> {
    Optional<PatientEntity> findByMedicalRecordNumber(String medicalRecordNumber);
}
