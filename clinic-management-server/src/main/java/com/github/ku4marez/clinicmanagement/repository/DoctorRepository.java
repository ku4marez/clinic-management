package com.github.ku4marez.clinicmanagement.repository;

import com.github.ku4marez.clinicmanagement.entity.DoctorEntity;
import com.github.ku4marez.clinicmanagement.repository.custom.CustomSearchRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorRepository extends MongoRepository<DoctorEntity, String>, CustomSearchRepository<DoctorEntity> {
}
