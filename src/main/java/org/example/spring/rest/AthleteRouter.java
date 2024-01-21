package org.example.spring.rest;

import org.springdoc.webflux.core.fn.SpringdocRouteBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class AthleteRouter {

    private static final String ATHLETES_PATH = "/athletes";
    
    @Bean
    public RouterFunction<ServerResponse> route(AthleteHandler handler) {
        return SpringdocRouteBuilder.route()
                .GET(ATHLETES_PATH, handler::getAllAthletes, ops -> ops.operationId("getAllAthletes"))
                .GET(ATHLETES_PATH + "/name/{name}", handler::getAthleteByName, ops -> ops.operationId("getAthleteByName"))
                .GET(ATHLETES_PATH + "/team/{name}", handler::getAllAthletesByTeamName, ops -> ops.operationId("getAthletesByTeamName"))
                .GET(ATHLETES_PATH + "/position/{name}", handler::getAllAthletesByPosition, ops -> ops.operationId("getAthletesByPositionName"))
                .GET(ATHLETES_PATH + "/conference/{name}", handler::getAllByConference, ops -> ops.operationId("getAthletesByConferenceName"))
                .GET(ATHLETES_PATH + "/division/{name}", handler::getAllByDivision, ops -> ops.operationId("getAthletesByDivisionName"))
                .POST(ATHLETES_PATH, handler::saveAthlete, ops -> ops.operationId("createAthlete"))
                .PUT(ATHLETES_PATH, handler::saveAthlete, ops -> ops.operationId("updateAthlete"))
                .DELETE(ATHLETES_PATH, handler::deleteAthlete, ops -> ops.operationId("deleteAthlete"))
                .build();
    }
}