package com.github.ku4marez.clinicmanagement.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "com.github.ku4marez.clinicmanagement.repository")
public class RepositoryConfiguration {
}
