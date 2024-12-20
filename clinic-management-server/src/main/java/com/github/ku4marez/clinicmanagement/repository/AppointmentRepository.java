package com.github.ku4marez.clinicmanagement.repository;

import com.github.ku4marez.clinicmanagement.entity.AppointmentEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AppointmentRepository extends MongoRepository<AppointmentEntity, String> {

    @Query("{ 'doctorId': ?0, 'dateTime': { $gte: ?1, $lt: ?2 } }")
    List<AppointmentEntity> findOverlappingAppointments(String doctorId, LocalDateTime startTime, LocalDateTime endTime);
}

