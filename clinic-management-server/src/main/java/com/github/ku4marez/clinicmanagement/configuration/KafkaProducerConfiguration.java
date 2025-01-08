package com.github.ku4marez.clinicmanagement.configuration;

import com.github.ku4marez.commonlibraries.util.KafkaProducerUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaProducerConfiguration {

    @Value("${spring.kafka.bootstrap-servers}")
    private static String BOOTSTRAP_SERVERS;

    @Bean
    public KafkaProducerUtil kafkaProducerUtil() {
        return new KafkaProducerUtil(BOOTSTRAP_SERVERS);
    }
}
