package org.example.spring.util;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.example.spring.model.Athlete;
import org.example.spring.model.Position;
import org.example.spring.model.Team;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Utility class to get data from the json resource files.
 */
public class DataUtil {

    private static final ObjectMapper objectMapper = new ObjectMapper(new JsonFactory());

    public static Set<Player> getPlayers() throws IOException {
        InputStream playerFile = new ClassPathResource("players.json").getInputStream();
        return objectMapper.readValue(playerFile, new TypeReference<>() {});
    }

    public static Set<Team> getTeams() throws IOException {
        InputStream teamFile = new ClassPathResource("teams.json").getInputStream();
        return objectMapper.readValue(teamFile, new TypeReference<>() {});
    }

    public static Set<Athlete> getAthletes() throws IOException {
        Set<Team> teams = getTeams();
        Set<Player> players = getPlayers();
        return players.stream()
                .map(player -> new Athlete(player.name, getPosition(player, teams)))
                .collect(Collectors.toSet());
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

    @Data
    private static class Player {
        String team;
        String position;
        String jersey;
        String name;
    }
}
