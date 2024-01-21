package org.example.spring.rest;

import org.example.spring.model.Team;
import org.example.spring.repository.TeamRepository;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@Component
public class TeamHandler {
    private final TeamRepository teamRepository;

    Function<Flux<Team>, Mono<ServerResponse>> createResponseFlux = teamFlux ->
            ServerResponse.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(teamFlux, Team.class);

    Function<Mono<Team>, Mono<ServerResponse>> createResponseMono = teamMono ->
            ServerResponse.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(teamMono, Team.class);

    public TeamHandler(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    public Mono<ServerResponse> getAllTeams(ServerRequest request) {
        return createResponseFlux.apply(teamRepository.findAll());
    }

    public Mono<ServerResponse> getByName(ServerRequest request) {
        return createResponseMono.apply(teamRepository.findByName(request.pathVariable("name")));
    }

    public Mono<ServerResponse> getByKey(ServerRequest request) {
        return createResponseMono.apply(teamRepository.findByKey(request.pathVariable("key")));
    }

    public Mono<ServerResponse> getAllByConference(ServerRequest request) {
        // Handling pageable is not straightforward in a reactive repository
        // You might need a custom method to handle this
        return createResponseFlux.apply(teamRepository.findAllByConference(request.pathVariable("name")));
    }

    public Mono<ServerResponse> getAllByDivision(ServerRequest request) {
        // Handling pageable is not straightforward in a reactive repository
        // You might need a custom method to handle this
        return createResponseFlux.apply(teamRepository.findAllByDivision(request.pathVariable("name")));
    }

    public Mono<ServerResponse> saveTeam(ServerRequest request) {
        return request.bodyToMono(Team.class)
                .flatMap(teamRepository::save)
                .flatMap(saved -> createResponseMono.apply(Mono.just(saved)));
    }

    public Mono<ServerResponse> deleteTeam(ServerRequest request) {
        return request.bodyToMono(Team.class)
                .flatMap((teamRepository::delete))
                .then(ServerResponse.noContent().build());
    }
}
