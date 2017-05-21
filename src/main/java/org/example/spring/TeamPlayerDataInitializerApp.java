package org.example.spring;

import org.example.spring.data.athlete.AthleteRepository;
import org.example.spring.data.team.TeamRepository;
import org.example.spring.model.Athlete;
import org.example.spring.model.Position;
import org.example.spring.model.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.repository.CrudRepository;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;
import java.util.Set;

/**
 * This is a command line app to remove all TeamPlayer data from the data store,
 * and then load data from the JSON team and player resource files and use the
 * repositories to populate the data store.
 * <p>
 * Running this application will delete all entries in the datastore for all
 * {@link Athlete}, {@link org.example.spring.model.Team},
 * and {@link org.example.spring.model.Position} entities.  Then it will re-populate
 * the data store with the entities found in the resource files.
 */
@SpringBootApplication
public class TeamPlayerDataInitializerApp implements CommandLineRunner {
    private AthleteRepository athleteRepository;
    private TeamRepository teamRepository;

    public TeamPlayerDataInitializerApp(@Autowired AthleteRepository athleteRepository,
                                        @Autowired TeamRepository teamRepository) throws IOException {
        this.athleteRepository = athleteRepository;
        this.teamRepository = teamRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(TeamPlayerDataInitializerApp.class, args);
    }

    /**
     * This method removes all {@link Athlete}s and {@link Team}s from the data store.  Then, it parses the
     * players and teams JSON files and re-populates the data store with fresh data.  After this processing
     * completes, it prints the number of teams and athletes that were saved to the data store.
     *
     * @param args ignored
     * @throws Exception if there was a problem parsing the JSON files
     */
    @Override
    public void run(String... args) throws Exception {
        Arrays.asList(athleteRepository, teamRepository).forEach(CrudRepository::deleteAll);

        Set<Team> teams = DataUtil.getTeams();
        Set<Athlete> athletes = DataUtil.getAthletes();

        teamRepository.insert(teams);
        athleteRepository.insert(athletes);

        System.out.printf("Saved %d teams and %d athletes.%n", teams.size(), athletes.size());
    }
}
