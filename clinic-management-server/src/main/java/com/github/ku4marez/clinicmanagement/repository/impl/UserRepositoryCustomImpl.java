package com.github.ku4marez.clinicmanagement.repository.impl;

import com.github.ku4marez.clinicmanagement.entity.UserEntity;
import com.github.ku4marez.clinicmanagement.repository.custom.UserRepositoryCustom;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.Optional;

@SuppressWarnings("unused")
public class UserRepositoryCustomImpl implements UserRepositoryCustom {

    private final MongoTemplate mongoTemplate;

    public UserRepositoryCustomImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Optional<UserEntity> findByEmailCaseInsensitive(String email) {
        Query query = new Query();
        query.addCriteria(Criteria.where("email").regex("^" + email + "$", "i"));
        return Optional.ofNullable(mongoTemplate.findOne(query, UserEntity.class));
    }
}
