package org.example.spring.rest;

import org.example.spring.model.Athlete;
import org.example.spring.repository.AthleteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.server.EntityResponse;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AthleteHandlerTest {

    @Mock
    AthleteRepository athleteRepository;

    @Mock
    Athlete athlete;

    @Mock
    private ServerRequest serverRequest;

    @Test
    public void getAllAthletes() {
        Flux<Athlete> athleteFlux = Flux.just(athlete);
        AthleteHandler handler = new AthleteHandler(athleteRepository);
        when(athleteRepository.findAll()).thenReturn(athleteFlux);
        List<Athlete> athletes = handler.getAllAthletes(null)
                .map(r -> (EntityResponse<Flux<Athlete>>) r)
                .map(EntityResponse::entity)
                .flatMapMany(Function.identity())
                .collectList()
                .block();
        assertEquals(1, athletes.size());
        assertEquals(athlete, athletes.get(0));
    }

    @Test
    void getAthleteByNameTest() {
        when(serverRequest.pathVariable("name")).thenReturn("John");
        when(athleteRepository.findByName("John")).thenReturn(Mono.just(new Athlete()));

        AthleteHandler athleteHandler = new AthleteHandler(athleteRepository);

        assertNotNull(athleteHandler.getAthleteByName(serverRequest));
        verify(athleteRepository, times(1)).findByName("John");
    }

    @Test
    void getAllAthletesByTeamNameTest() {
        when(serverRequest.pathVariable("name")).thenReturn("teamName");
        when(athleteRepository.findAllByPositionTeamName("teamName")).thenReturn(Flux.empty());

        AthleteHandler athleteHandler = new AthleteHandler(athleteRepository);

        assertNotNull(athleteHandler.getAllAthletesByTeamName(serverRequest));
        verify(athleteRepository, times(1)).findAllByPositionTeamName("teamName");
    }

    @Test
    void getAllAthletesByPositionTest() {
        when(serverRequest.pathVariable("name")).thenReturn("position");
        when(athleteRepository.findAllByPositionName("position")).thenReturn(Flux.empty());

        AthleteHandler athleteHandler = new AthleteHandler(athleteRepository);

        assertNotNull(athleteHandler.getAllAthletesByPosition(serverRequest));
        verify(athleteRepository, times(1)).findAllByPositionName("position");
    }

    @Test
    void getAllByConferenceTest() {
        when(serverRequest.pathVariable("name")).thenReturn("conference");
        when(athleteRepository.findAllByPositionTeamConference("conference")).thenReturn(Flux.empty());

        AthleteHandler athleteHandler = new AthleteHandler(athleteRepository);

        assertNotNull(athleteHandler.getAllByConference(serverRequest));
        verify(athleteRepository, times(1)).findAllByPositionTeamConference("conference");
    }

    @Test
    void getAllByDivisionTest() {
        when(serverRequest.pathVariable("name")).thenReturn("division");
        when(athleteRepository.findAllByPositionTeamDivision("division")).thenReturn(Flux.empty());

        AthleteHandler athleteHandler = new AthleteHandler(athleteRepository);

        assertNotNull(athleteHandler.getAllByDivision(serverRequest));
        verify(athleteRepository, times(1)).findAllByPositionTeamDivision("division");
    }

    @Test
    void saveAthleteTest() {
        Mono<Athlete> athleteMono = Mono.just(athlete);

        when(serverRequest.bodyToMono(Athlete.class)).thenReturn(athleteMono);
        when(athleteRepository.save(any(Athlete.class))).thenReturn(athleteMono);

        AthleteHandler athleteHandler = new AthleteHandler(athleteRepository);
        Mono<ServerResponse> result = athleteHandler.saveAthlete(serverRequest);

        result.subscribe(response -> assertEquals(HttpStatus.OK, response.statusCode()));

        verify(serverRequest, times(1)).bodyToMono(Athlete.class);
        verify(athleteRepository, times(1)).save(athlete);
    }

    @Test
    void deleteAthleteTest() {
        Mono<Void> voidMono = Mono.empty();
        Mono<Athlete> athleteMono = Mono.just(athlete);

        when(serverRequest.bodyToMono(Athlete.class)).thenReturn(athleteMono);
        when(athleteRepository.delete(any(Athlete.class))).thenReturn(voidMono);

        AthleteHandler athleteHandler = new AthleteHandler(athleteRepository);
        Mono<ServerResponse> result = athleteHandler.deleteAthlete(serverRequest);

        result.subscribe(response -> assertEquals(HttpStatus.NO_CONTENT, response.statusCode()));

        verify(serverRequest, times(1)).bodyToMono(Athlete.class);
        verify(athleteRepository, times(1)).delete(athlete);
    }
}