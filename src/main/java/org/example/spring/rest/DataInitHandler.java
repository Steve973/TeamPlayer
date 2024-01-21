package org.example.spring.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.example.spring.repository.AthleteRepository;
import org.example.spring.repository.TeamRepository;
import org.example.spring.util.DataUtil;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class DataInitHandler {

    private final AthleteRepository athleteRepository;

    private final TeamRepository teamRepository;

    private final ObjectMapper objectMapper;

    public DataInitHandler(AthleteRepository athleteRepository, TeamRepository teamRepository,
                           ObjectMapper objectMapper) {
        this.athleteRepository = athleteRepository;
        this.teamRepository = teamRepository;
        this.objectMapper = objectMapper;
    }

    public Mono<ServerResponse> init(ServerRequest request) {
        return Flux.concat(athleteRepository.deleteAll(), teamRepository.deleteAll())
                .then(
                        Mono.zip(
                                teamRepository.insert(DataUtil.getTeamsFlux(objectMapper)).count(),
                                athleteRepository.insert(DataUtil.getAthletes(objectMapper)).count()))
                .map(tuple -> String.format("Saved %d teams and %d athletes", tuple.getT1(), tuple.getT2()))
                .flatMap(result -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(result));
    }
}
