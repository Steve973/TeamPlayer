package org.example.spring.rest;

import org.example.spring.model.Athlete;
import org.example.spring.repository.AthleteRepository;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.CorePublisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@Service
public class AthleteHandler {

    private final AthleteRepository athleteRepository;

    Function<CorePublisher<Athlete>, Mono<ServerResponse>> createResponse = athletePublisher ->
            ServerResponse.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(athletePublisher, Athlete.class);
    public AthleteHandler(AthleteRepository athleteRepository) {
        this.athleteRepository = athleteRepository;
    }

    public Mono<ServerResponse> getAllAthletes(ServerRequest request) {
        Flux<Athlete> athletes = athleteRepository.findAll();
        return createResponse.apply(athletes);
    }

    public Mono<ServerResponse> getAthleteByName(ServerRequest request) {
        Mono<Athlete> athlete = athleteRepository.findByName(request.pathVariable("name"));
        return createResponse.apply(athlete);
    }

    public Mono<ServerResponse> getAllAthletesByTeamName(ServerRequest request) {
        Flux<Athlete> athletes = athleteRepository.findAllByPositionTeamName(request.pathVariable("name"));
        return createResponse.apply(athletes);
    }

    public Mono<ServerResponse> getAllAthletesByPosition(ServerRequest request) {
        Flux<Athlete> athletes = athleteRepository.findAllByPositionName(request.pathVariable("name"));
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(athletes, Athlete.class);
    }

    public Mono<ServerResponse> getAllByConference(ServerRequest request) {
        Flux<Athlete> athletes = athleteRepository.findAllByPositionTeamConference(request.pathVariable("name"));
        return createResponse.apply(athletes);
    }

    public Mono<ServerResponse> getAllByDivision(ServerRequest request) {
        Flux<Athlete> athletes = athleteRepository.findAllByPositionTeamDivision(request.pathVariable("name"));
        return createResponse.apply(athletes);
    }

    public Mono<ServerResponse> saveAthlete(ServerRequest request) {
        return request.bodyToMono(Athlete.class)
                .flatMap(athleteRepository::save)
                .flatMap(saved -> createResponse.apply(Mono.just(saved)));
    }

    public Mono<ServerResponse> deleteAthlete(ServerRequest request) {
        return request.bodyToMono(Athlete.class)
                .flatMap((athleteRepository::delete))
                .then(ServerResponse.noContent().build());
    }
}
