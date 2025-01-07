package com.github.ku4marez.clinicmanagement.configuration;

import com.github.ku4marez.commonlibraries.util.KafkaProducerUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaProducerConfiguration {

    private static final String BOOTSTRAP_SERVERS = "localhost:9092";

    @Bean
    public KafkaProducerUtil kafkaProducerUtil() {
        return new KafkaProducerUtil(BOOTSTRAP_SERVERS);
    }
}
