package com.github.ku4marez.clinicmanagement.database;

import com.mongodb.client.MongoClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class MongoDbConnectionTest {

    @Autowired
    private MongoClient mongoClient;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Test
    void testMongoClientConnection() {
        assertThat(mongoClient).isNotNull();
    }

    @Test
    void testMongoTemplateConnection() {
        assertThat(mongoTemplate).isNotNull();

        String databaseName = mongoTemplate.getDb().getName();
        assertThat(databaseName).isEqualTo("clinic_management");
    }
}
