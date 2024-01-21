package org.example.spring.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.SneakyThrows;
import org.example.spring.model.Athlete;
import org.example.spring.model.Position;
import org.example.spring.model.Team;
import org.springframework.core.io.ClassPathResource;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.function.Supplier;

/**
 * Utility class to get data from the json resource files.
 */
public class DataUtil {

    private static final Supplier<InputStream> playerFileSupplier = () -> getStreamFromFile("players.json");

    private static final Supplier<InputStream> teamFileSupplier = () -> getStreamFromFile("teams.json");

    private DataUtil() {
        throw new IllegalStateException("Util class -- do not attempt to create an instance!");
    }

    private static InputStream getStreamFromFile(String fileName) {
        try {
            return new ClassPathResource(fileName).getInputStream();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    public static Flux<Athlete> getAthletes(ObjectMapper objectMapper) {
        return getPlayersFlux(objectMapper)
                .flatMap(player -> getTeamsFlux(objectMapper)
                        .filter(team -> team.getKey().equals(player.team))
                        .switchIfEmpty(Mono.error(new IllegalArgumentException("Team not found with key: " + player.team)))
                        .flatMap(team -> getPosition(player, team))
                        .map(position -> new Athlete(player.name, position)));
    }

    @SneakyThrows({DatabindException.class, IOException.class})
    static Flux<Player> getPlayersFlux(ObjectMapper objectMapper) {
        return Flux.fromIterable(objectMapper.readValue(playerFileSupplier.get(), new TypeReference<List<Player>>() {
        })).map(t -> t);
    }

    @SneakyThrows({DatabindException.class, IOException.class})
    public static Flux<Team> getTeamsFlux(ObjectMapper objectMapper) {
        return Flux.fromIterable(objectMapper.readValue(teamFileSupplier.get(), new TypeReference<List<Team>>() {
        })).map(t -> t);
    }

    private static Mono<Position> getPosition(Player player, Team team) {
        return Mono.justOrEmpty(player)
                .filter(p -> p.position != null)
                .map(p -> new Position(team, p.position, p.jersey));
    }

    @Data
    public static class Player {
        String team;
        String position;
        String jersey;
        String name;
    }
}
