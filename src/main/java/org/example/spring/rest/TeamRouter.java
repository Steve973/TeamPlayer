package org.example.spring.rest;

import org.springdoc.webflux.core.fn.SpringdocRouteBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class TeamRouter {

    private static final String TEAMS_PATH = "/teams";

    @Bean
    public RouterFunction<ServerResponse> teamRoute(TeamHandler handler) {
        return SpringdocRouteBuilder.route()
                .GET(TEAMS_PATH, handler::getAllTeams, ops -> ops.operationId("getAllTeams"))
                .GET(TEAMS_PATH + "/name/{name}", handler::getByName, ops -> ops.operationId("getTeamByName"))
                .GET(TEAMS_PATH + "/key/{key}", handler::getByKey, ops -> ops.operationId("getTeamByKey"))
                .GET(TEAMS_PATH + "/conference/{name}", handler::getAllByConference, ops -> ops.operationId("getTeamsByConferenceName"))
                .GET(TEAMS_PATH + "/division/{name}", handler::getAllByDivision, ops -> ops.operationId("getTeamsByDivisionName"))
                .POST(TEAMS_PATH, handler::saveTeam, ops -> ops.operationId("createTeam"))
                .DELETE(TEAMS_PATH, handler::deleteTeam, ops -> ops.operationId("deleteTeam"))
                .build();
    }
}