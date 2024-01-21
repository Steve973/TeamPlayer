package org.example.spring;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * This is the main entry point for the TeamPlayer application.
 */
@Slf4j
@SpringBootApplication
public class TeamPlayerApp {

    public static void main(String[] args) {
        SpringApplication.run(TeamPlayerApp.class, args);
    }
}
