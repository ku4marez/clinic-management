package com.github.ku4marez.clinicmanagement.repository.custom;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;
import java.util.Optional;

@SuppressWarnings("unused")
public class CustomSearchRepositoryImpl<T> implements CustomSearchRepository<T> {

    private final MongoTemplate mongoTemplate;
    private final Class<T> entityClass;

    public CustomSearchRepositoryImpl(MongoTemplate mongoTemplate,
                                      Class<T> entityClass) {
        this.mongoTemplate = mongoTemplate;
        this.entityClass = entityClass;
    }

    @Override
    public Optional<T> findByEmailCaseInsensitive(String email) {
        Query query = new Query();
        query.addCriteria(Criteria.where("email").regex("^" + email + "$", "i"));
        return Optional.ofNullable(mongoTemplate.findOne(query, entityClass));
    }

    @Override
    public Page<T> searchByName(String name, Pageable pageable) {
        Query query = new Query();
        query.addCriteria(Criteria.where("firstName").regex(name, "i")); // Case-insensitive regex search
        long total = mongoTemplate.count(query, entityClass);
        query.with(pageable);
        List<T> results = mongoTemplate.find(query, entityClass);
        return new PageImpl<>(results, pageable, total);
    }
}
