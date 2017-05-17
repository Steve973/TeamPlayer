package org.example.spring;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.example.spring.data.AthleteRepository;
import org.example.spring.data.PositionRepository;
import org.example.spring.data.TeamRepository;
import org.example.spring.model.Athlete;
import org.example.spring.model.Position;
import org.example.spring.model.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.repository.CrudRepository;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
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
    private File playerFile = new ClassPathResource("players.json").getFile();
    private File teamFile = new ClassPathResource("teams.json").getFile();
    @Autowired
    private AthleteRepository athleteRepository;
    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private PositionRepository positionRepository;

    public TeamPlayerDataInitializerApp() throws IOException {
    }

    public static void main(String[] args) {
        SpringApplication.run(TeamPlayerDataInitializerApp.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Gson gson = new Gson();
        Arrays.asList(athleteRepository, teamRepository, positionRepository).forEach(CrudRepository::deleteAll);

        Set<Team> teams = gson.fromJson(new FileReader(teamFile), new TypeToken<Set<Team>>() {}.getType());
        Set<Player> players = gson.fromJson(new FileReader(playerFile), new TypeToken<Set<Player>>() {}.getType());
        Set<Athlete> athletes = players.stream().map(player -> new Athlete(player.name)).collect(Collectors.toSet());
        Set<Position> positions = getPositions(players, athletes, teams);

        teamRepository.save(teams);
        athleteRepository.save(athletes);
        positionRepository.save(positions);

        System.out.printf("Saved %d teams, %d athletes, and %d positions.%n", teams.size(), athletes.size(), positions.size());
    }

    private static Set<Position> getPositions(final Set<Player> players, final Set<Athlete> people, final Set<Team> teams) {
        return players.stream()
                .filter(player -> player.position != null)
                .map(player -> {
                    Athlete athlete = people.stream()
                            .filter(person -> person.getName().equals(player.name))
                            .findFirst()
                            .orElseThrow(() -> new IllegalArgumentException("No athlete found with name: " + player.name));
                    Team team = teams.stream()
                            .filter(aTeam -> aTeam.getKey().equals(player.team))
                            .findFirst()
                            .orElseThrow(() -> new IllegalArgumentException("No team found with name: " + player.team));
                    return new Position(athlete, team, player.position, player.jersey);
                }).collect(Collectors.toSet());
    }

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
