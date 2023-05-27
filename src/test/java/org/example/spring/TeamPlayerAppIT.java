package org.example.spring;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.spring.data.athlete.AthleteRepository;
import org.example.spring.data.team.TeamRepository;
import org.example.spring.model.Athlete;
import org.example.spring.model.Team;
import org.example.spring.service.DataInitService;
import org.example.spring.util.DataUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * This is a functional test for the app.
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = TeamPlayerApp.class)
public class TeamPlayerAppIT {

    @Autowired
    AthleteRepository athleteRepository;

    @Autowired
    TeamRepository teamRepository;

    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    DataInitService dataInitService;

    @Autowired
    private TestRestTemplate restTemplate;

    private boolean initialized = false;

    private Set<Team> teams;

    private Set<Athlete> athletes;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() throws Exception {
        if (!initialized) {
            dataInitService.init();
            teams = DataUtil.getTeams();
            athletes = DataUtil.getAthletes();
            initialized = true;
        }
        objectMapper = new ObjectMapper(new JsonFactory());
    }

    @Test
    public void testContextLoads() {
        assertThat(athleteRepository).isNotNull();
        assertThat(teamRepository).isNotNull();
        assertThat(mongoTemplate).isNotNull();
        assertThat(restTemplate).isNotNull();
    }

    @Test
    public void testFindAllTeams() throws JsonProcessingException {
        ResponseEntity<String> response = restTemplate.getForEntity("/teams", String.class);
        Set<Team> results = objectMapper.readValue(response.getBody(), new TypeReference<>() {});
        assertEquals(results.size(), teams.size(), "Team list must be the same size as the expected team list");
        assertTrue(results.stream().allMatch(result -> teams.stream().anyMatch(team -> team.equals(result))), "Team list must equal the expected team list");
    }

    @Test
    public void testFindAllAthletes() throws JsonProcessingException {
        ResponseEntity<String> response = restTemplate.getForEntity("/athletes?page=0&size=" + athletes.size(), String.class);
        Set<Athlete> results = objectMapper.readValue(response.getBody(), new TypeReference<>() {});
        assertEquals(results.size(), athletes.size(), "Athlete list must be the same size as the expected athlete list");
        assertTrue(results.stream().allMatch(result -> athletes.stream().anyMatch(athlete -> athlete.equals(result))), "Athlete list must equal the expected athlete list");
    }

    private static class PagedAthleteResult {
        Set<Athlete> content;
        String totalPages;
        String totalElements;
        String last;
        String size;
        String number;
        String first;
        String numberOfElements;

    }
}
