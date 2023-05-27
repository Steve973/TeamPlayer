package org.example.spring;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

/**
 * This is the main entry point and configuration for the TeamPlayer
 * application, annotated with {@link SpringBootApplication} to enable
 * configuration, classpath component scanning, etc.
 */
@Slf4j
@SpringBootApplication
public class TeamPlayerApp {

    public static void main(String[] args) {
        SpringApplication.run(TeamPlayerApp.class, args);
    }

    @Bean
    @Primary
    public ObjectMapper objectMapper() {
        return new CustomObjectMapper();
    }

    /**
     * Setting serialization options for the REST endpoints.
     */
    public static class CustomObjectMapper extends ObjectMapper {
        public CustomObjectMapper() {
            super(new JsonFactory());
            setSerializationInclusion(JsonInclude.Include.NON_NULL);
            configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
            configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
            configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            enable(SerializationFeature.INDENT_OUTPUT);
        }
    }
}
