package org.example.spring;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.utility.DockerImageName;

/**
 * This is the main entry point for the TeamPlayer application.
 */
@Slf4j
@SpringBootApplication
public class TestApp {

    public static void main(String[] args) {
        SpringApplication.from(TeamPlayerApp::main)
                .with(ContainerConfig.class)
                .run(args);
    }

    @Configuration(proxyBeanMethods = false)
    public static class ContainerConfig {

        @Bean
        @ServiceConnection
        public MongoDBContainer mongo() {
            return new MongoDBContainer(DockerImageName.parse("mongo:7"))
                    .withExposedPorts(27017)
                    .waitingFor(Wait.forListeningPort());
        }
    }
}
