package com.github.ku4marez.clinicmanagement.configuration;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
public class MongoConfiguration {

    @Value("${spring.data.mongodb.host}")
    private String mongoHost;

    @Value("${spring.data.mongodb.port}")
    private int mongoPort;

    @Value("${spring.data.mongodb.database}")
    private String mongoDatabase;

    @Value("${spring.data.mongodb.username}")
    private String username;

    @Value("${spring.data.mongodb.password}")
    private String password;

    @Bean
    public MongoClient mongoClient() {
        // Connection string with credentials
        String connectionString = String.format(
                "mongodb://%s:%s@%s:%d/%s",
                username,
                password,
                mongoHost,
                mongoPort,
                mongoDatabase
        );
        return MongoClients.create(connectionString);
    }

    @Bean
    public MongoTemplate mongoTemplate(MongoClient mongoClient) {
        return new MongoTemplate(mongoClient, mongoDatabase);
    }
}
