package org.example.spring;

import org.example.spring.model.Athlete;
import org.example.spring.model.Team;
import org.example.spring.repository.AthleteRepository;
import org.example.spring.repository.TeamRepository;
import org.example.spring.rest.DataInitHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * This is a functional test for the app.
 */
@Testcontainers
@Import(TestApp.ContainerConfig.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = TestApp.class)
public class TeamPlayerAppIT {

    @Autowired
    MongoDBContainer mongo;

    @Autowired
    AthleteRepository athleteRepository;

    @Autowired
    TeamRepository teamRepository;

    @Autowired
    DataInitHandler dataInitHandler;

    @Autowired
    private WebTestClient webTestClient;

    @BeforeEach
    public void setup() {
        assertTrue(mongo.isRunning());
        webTestClient.put()
                .uri("/init")
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .isEqualTo("Saved 30 teams and 811 athletes");
    }

    @Test
    void testGetAllAthletes() {
        webTestClient.get()
                .uri("/athletes")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Athlete.class)
                .hasSize(811);
    }

    @Test
    void testGetAllTeams() {
        webTestClient.get()
                .uri("/teams")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Team.class)
                .hasSize(30);
    }
}
