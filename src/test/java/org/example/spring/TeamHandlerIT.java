package org.example.spring;

import org.example.spring.model.Team;
import org.example.spring.repository.TeamRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = TeamPlayerApp.class)
@AutoConfigureWebTestClient
@Testcontainers
public class TeamHandlerIT {

    @Container
    public static MongoDBContainer mongo = new MongoDBContainer(DockerImageName.parse("mongo:7"))
            .withExposedPorts(27017)
            .waitingFor(Wait.forListeningPort());

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private TeamRepository teamRepository;

    private Team team;

    @BeforeEach
    void setUp() {
        teamRepository.deleteAll().subscribe();
        this.team = new Team("Team Name", "key_1", "conf_1", "div_1");
        teamRepository.save(this.team).block();
    }

    @Test
    void testGetAllTeams() {
        webTestClient.get()
                .uri("/teams")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Team.class)
                .contains(this.team);
    }

    @Test
    void testGetByName() {
        webTestClient.get()
                .uri("/teams/name/" + this.team.getName())
                .exchange()
                .expectStatus().isOk()
                .expectBody(Team.class)
                .isEqualTo(this.team);
    }

    @Test
    void testGetByKey() {
        webTestClient.get()
                .uri("/teams/key/" + this.team.getKey())
                .exchange()
                .expectStatus().isOk()
                .expectBody(Team.class)
                .isEqualTo(this.team);
    }

    @Test
    void testGetAllByConference() {
        webTestClient.get()
                .uri("/teams/conference/" + this.team.getConference())
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Team.class)
                .contains(this.team);
    }

    @Test
    void testGetAllByDivision() {
        webTestClient.get()
                .uri("/teams/division/" + this.team.getDivision())
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Team.class)
                .contains(this.team);
    }

    @Test
    void testSaveTeam() {
        Team newTeam = new Team("Team 2", "key_2", "conf_2", "div_2");
        Team persistedTeam = webTestClient.post()
                .uri("/teams")
                .body(Mono.just(newTeam), Team.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Team.class)
                .returnResult()
                .getResponseBody();
        newTeam.setId(persistedTeam.getId());
        assertEquals(newTeam, persistedTeam);
    }

    @Test
    void testDeleteTeam() {
        webTestClient.method(HttpMethod.DELETE)
                .uri("/teams")
                .body(Mono.just(this.team), Team.class)
                .exchange()
                .expectStatus().isNoContent();
        assertTrue(teamRepository.findByName(this.team.getName()).blockOptional().isEmpty());
    }

    @AfterEach
    void cleanup() {
        teamRepository.deleteAll().subscribe();
    }
}