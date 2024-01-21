package org.example.spring.rest;

import org.example.spring.model.Team;
import org.example.spring.repository.TeamRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TeamHandlerTest {

    @Mock
    TeamRepository teamRepository;

    @Mock
    Team team;

    @Mock
    private ServerRequest serverRequest;

    @Test
    public void getAllTeams() {
        Flux<Team> teamFlux = Flux.just(team);
        TeamHandler handler = new TeamHandler(teamRepository);
        when(teamRepository.findAll()).thenReturn(teamFlux);

        Mono<ServerResponse> teams = handler.getAllTeams(null);
        assertNotNull(teams);
        // Additional assertions based on response
    }

    @Test
    void getByName() {
        when(serverRequest.pathVariable("name")).thenReturn("TeamName");
        when(teamRepository.findByName("TeamName")).thenReturn(Mono.just(new Team()));

        TeamHandler teamHandler = new TeamHandler(teamRepository);
        
        assertNotNull(teamHandler.getByName(serverRequest));
    }

    @Test
    void getByKey() {
        when(serverRequest.pathVariable("key")).thenReturn("TeamKey");
        when(teamRepository.findByKey("TeamKey")).thenReturn(Mono.just(new Team()));

        TeamHandler teamHandler = new TeamHandler(teamRepository);
        
        assertNotNull(teamHandler.getByKey(serverRequest));
    }

    @Test
    void getAllByConference() {
        when(serverRequest.pathVariable("name")).thenReturn("ConferenceName");
        when(teamRepository.findAllByConference("ConferenceName")).thenReturn(Flux.just(team));

        TeamHandler handler = new TeamHandler(teamRepository);
        Mono<ServerResponse> response = handler.getAllByConference(serverRequest);

        assertNotNull(response);
    }

    @Test
    void getAllByDivision() {
        when(serverRequest.pathVariable("name")).thenReturn("DivisionName");
        when(teamRepository.findAllByDivision("DivisionName")).thenReturn(Flux.just(team));

        TeamHandler handler = new TeamHandler(teamRepository);
        Mono<ServerResponse> response = handler.getAllByDivision(serverRequest);

        assertNotNull(response);
    }

    @Test
    void saveTeam() {
        Mono<Team> monoTeam = Mono.just(team);

        when(serverRequest.bodyToMono(Team.class)).thenReturn(monoTeam);
        when(teamRepository.save(any(Team.class))).thenReturn(monoTeam);

        TeamHandler handler = new TeamHandler(teamRepository);
        Mono<ServerResponse> response = handler.saveTeam(serverRequest);

        assertTrue(response.block().statusCode().equals(HttpStatus.OK));
        verify(serverRequest, times(1)).bodyToMono(Team.class);
        verify(teamRepository, times(1)).save(team);
    }

    @Test
    void deleteTeam() {
        Mono<Void> voidMono = Mono.empty();
        Mono<Team> teamMono = Mono.just(team);

        when(serverRequest.bodyToMono(Team.class)).thenReturn(teamMono);
        when(teamRepository.delete(any(Team.class))).thenReturn(voidMono);

        TeamHandler teamHandler = new TeamHandler(teamRepository);
        Mono<ServerResponse> result = teamHandler.deleteTeam(serverRequest);

        result.subscribe(response -> assertEquals(HttpStatus.NO_CONTENT, response.statusCode()));

        verify(serverRequest, times(1)).bodyToMono(Team.class);
        verify(teamRepository, times(1)).delete(team);
    }
}