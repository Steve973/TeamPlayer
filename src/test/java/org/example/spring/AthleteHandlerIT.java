package org.example.spring;

import org.example.spring.model.Athlete;
import org.example.spring.model.Position;
import org.example.spring.model.Team;
import org.example.spring.repository.AthleteRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
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
public class AthleteHandlerIT {

    @Container
    @ServiceConnection
    public static MongoDBContainer mongo = new MongoDBContainer(DockerImageName.parse("mongo:7"))
            .withExposedPorts(27017)
            .waitingFor(Wait.forListeningPort());

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private AthleteRepository athleteRepository;

    private Athlete athlete;

    private Position position;

    private Team team;

    @BeforeEach
    void setUp() {
        athleteRepository.deleteAll().subscribe();

        this.team = new Team("Team Name", "key_1", "conf_1", "div_1");
        this.position = new Position(this.team, "Position Name", "jersey_1");
        this.athlete = new Athlete("Athlete Name", this.position);

        athleteRepository.save(this.athlete).block();
    }


    @Test
    void testGetAllAthletes() {
        webTestClient.get()
                .uri("/athletes")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Athlete.class)
                .contains(this.athlete);
    }

    @Test
    void testGetAthleteByName() {
        webTestClient.get()
                .uri("/athletes/name/" + this.athlete.getName())
                .exchange()
                .expectStatus().isOk()
                .expectBody(Athlete.class)
                .isEqualTo(this.athlete);
    }

    @Test
    void testGetAllAthletesByTeamName() {
        webTestClient.get()
                .uri("/athletes/team/" + this.athlete.getPosition().getTeam().getName())
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Athlete.class)
                .contains(this.athlete);
    }

    @Test
    void testGetAllAthletesByPosition() {
        webTestClient.get()
                .uri("/athletes/position/" + this.athlete.getPosition().getName())
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Athlete.class)
                .contains(this.athlete);
    }

    @Test
    void testGetAllByConference() {
        webTestClient.get()
                .uri("/athletes/conference/" + this.athlete.getPosition().getTeam().getConference())
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Athlete.class)
                .contains(this.athlete);
    }

    @Test
    void testGetAllByDivision() {
        webTestClient.get()
                .uri("/athletes/division/" + this.athlete.getPosition().getTeam().getDivision())
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Athlete.class)
                .contains(this.athlete);
    }

    @Test
    void testSaveAthlete() {
        Athlete newAthlete = new Athlete("Athlete 2", this.position);
        Athlete persistedAthlete = webTestClient.post()
                .uri("/athletes")
                .body(Mono.just(newAthlete), Athlete.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Athlete.class)
                .returnResult()
                .getResponseBody();
        newAthlete.setId(newAthlete.getId() == null && persistedAthlete.getId() != null ?
                persistedAthlete.getId() : newAthlete.getId());
        assertEquals(newAthlete, persistedAthlete);
    }

    @Test
    void testDeleteAthlete() {
        webTestClient.method(HttpMethod.DELETE)
                .uri("/athletes")
                .bodyValue(this.athlete)
                .exchange()
                .expectStatus().isNoContent();

        assertTrue(athleteRepository.findByName(this.athlete.getName()).blockOptional().isEmpty());
    }

    @AfterEach
    void cleanup() {
        athleteRepository.deleteAll().subscribe();
    }
}
