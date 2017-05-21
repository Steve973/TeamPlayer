package org.example.spring;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.example.spring.data.athlete.AthleteRepository;
import org.example.spring.data.team.TeamRepository;
import org.example.spring.model.Athlete;
import org.example.spring.model.Team;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Set;

import static junit.framework.TestCase.assertTrue;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * This is a functional test for the app.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = TeamPlayerApp.class)
public class TeamPlayerAppIT {
    @Autowired
    AthleteRepository athleteRepository;
    @Autowired
    TeamRepository teamRepository;
    @Autowired
    MongoTemplate mongoTemplate;
    @Autowired
    TeamPlayerDataInitializerApp initializerApp;
    @Autowired
    private TestRestTemplate restTemplate;
    private boolean initialized = false;
    private Set<Team> teams;
    private Set<Athlete> athletes;

    @Before
    public void setup() throws Exception {
        if (!initialized) {
            initializerApp.run(null);
            teams = DataUtil.getTeams();
            athletes = DataUtil.getAthletes();
            initialized = true;
        }
    }

    @Test
    public void testContextLoads() {
        assertThat(athleteRepository).isNotNull();
        assertThat(teamRepository).isNotNull();
        assertThat(mongoTemplate).isNotNull();
        assertThat(restTemplate).isNotNull();
    }

    @Test
    public void testFindAllTeams() {
        ResponseEntity<String> response = restTemplate.getForEntity("/teams", String.class);
        Set<Team> results = new Gson().fromJson(response.getBody(), new TypeToken<Set<Team>>() {}.getType());
        assertTrue("Team list must be the same size as the expected team list", results.size() == teams.size());
        assertTrue("Team list must equal the expected team list",
                results.stream().allMatch(result -> teams.stream().anyMatch(team -> team.equals(result))));
    }

    @Test
    public void testFindAllAthletes() {
        ResponseEntity<String> response = restTemplate.getForEntity("/athletes?page=0&size=" + athletes.size(), String.class);
        Set<Athlete> results = new Gson().fromJson(response.getBody(), PagedAthleteResult.class).content;
        assertTrue("Athlete list must be the same size as the expected athlete list", results.size() == athletes.size());
        assertTrue("Athlete list must equal the expected athlete list",
                results.stream().allMatch(result -> athletes.stream().anyMatch(athlete -> athlete.equals(result))));
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
