package com.github.ku4marez.clinicmanagement.configuration;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@EnableCaching
@Configuration
@Profile("!test")
public class CacheConfiguration {}
