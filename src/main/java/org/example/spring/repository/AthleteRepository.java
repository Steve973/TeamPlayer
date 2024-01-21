package org.example.spring.repository;

import org.example.spring.model.Athlete;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * This is the Repository interface for the Athlete objects.
 */
@Repository
public interface AthleteRepository extends ReactiveMongoRepository<Athlete, String> {

    /**
     * Finds all Athletes by name.
     *
     * @param name The name to search Athletes by
     * @return the Athlete with the given name
     */
    Mono<Athlete> findByName(String name);

    /**
     * Finds all Athletes by Team name.
     *
     * @param team Team name to use to search for Athletes
     * @return all Athletes associated with the given Team
     */
    Flux<Athlete> findAllByPositionTeamName(String team);

    /**
     * Find all Athletes that have the given Position.
     *
     * @param position the Position name to search for Athletes by
     * @return all Athletes associated with the given Position
     */
    Flux<Athlete> findAllByPositionName(String position);

    /**
     * Finds all Athletes in the given Conference.
     *
     * @param conference the name of the Conference to search for Athletes by
     * @return all Athletes associated with the given Conference
     */
    Flux<Athlete> findAllByPositionTeamConference(String conference);

    /**
     * Finds all Athletes in the given Division.
     *
     * @param division the name of the Division to search for Athletes by
     * @return all Athletes associated with the given Division
     */
    Flux<Athlete> findAllByPositionTeamDivision(String division);
}
