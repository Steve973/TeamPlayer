package org.example.spring;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.example.spring.data.athlete.AthleteRepository;
import org.example.spring.data.team.TeamRepository;
import org.example.spring.model.Athlete;
import org.example.spring.model.Position;
import org.example.spring.model.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.repository.CrudRepository;

import java.io.*;
import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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
    private InputStream playerFile = new ClassPathResource("/players.json").getInputStream();
    private InputStream teamFile = new ClassPathResource("/teams.json").getInputStream();
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
     * The {@link Position} information must be parsed from the teams.xml file, so this parses those objects
     * to create {@link Athlete} and {@link Team} objects which are then used to create the {@link Position} instance.
     *
     * @param player the {@link Player} that may have a position to parse
     * @param teams  a collection of {@link Team}s
     * @return the {@link Position}s that are the association of an {@link Athlete} on a {@link Team}
     */
    private static Position getPosition(final Player player, final Set<Team> teams) {
        return Optional.ofNullable(player)
                .filter(p -> p.position != null)
                .map(p -> {
                    Team team = teams.stream()
                            .filter(aTeam -> aTeam.getKey().equals(player.team))
                            .findFirst()
                            .orElseThrow(() -> new IllegalArgumentException("No team found with name: " + player.team));
                    return new Position(team, player.position, player.jersey);
                }).orElse(null);
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
        Gson gson = new Gson();
        Arrays.asList(athleteRepository, teamRepository).forEach(CrudRepository::deleteAll);

        Set<Team> teams = gson.fromJson(new InputStreamReader(teamFile), new TypeToken<Set<Team>>() {}.getType());
        Set<Player> players = gson.fromJson(new InputStreamReader(playerFile), new TypeToken<Set<Player>>() {}.getType());
        Set<Athlete> athletes = players.stream()
                .map(player -> new Athlete(player.name, getPosition(player, teams)))
                .collect(Collectors.toSet());

        teamRepository.insert(teams);
        athleteRepository.insert(athletes);

        System.out.printf("Saved %d teams and %d athletes.%n", teams.size(), athletes.size());
    }

    /**
     * This is an object for intermediate processing of the player JSON file.  It is a private static class because
     * it is needed nowhere else.  There is a custom toString method for debugging purposes.
     */
    private static class Player {
        String team;
        String position;
        String jersey;
        String name;

        @Override
        public String toString() {
            return String.format("Player: %s, %s, %s, %s", name, team, position, jersey);
        }
    }
}
