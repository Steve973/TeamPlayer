package org.example.spring.repository;

import org.example.spring.model.Team;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * This is the Repository interface for the Team objects.
 */
@Repository
public interface TeamRepository extends ReactiveMongoRepository<Team, String> {

    /**
     * Finds all Athletes by name.
     *
     * @param name The name to search Athletes by
     * @return the Athlete with the given name
     */
    Mono<Team> findByName(String name);

    /**
     * Teams have a unique key, like "PHI" for the Philadelphia Flyers.
     * This finds the team associated with the given key.
     *
     * @param key a unique three letter key for a Team
     * @return the Team associated with the given key
     */
    Mono<Team> findByKey(String key);

    /**
     * Find all Teams by the given Conference.
     *
     * @param conference the name of the Conference to search for teams by
     * @return all Teams in the specified Conference
     */
    Flux<Team> findAllByConference(String conference);

    /**
     * Find all teams by the given Division.
     *
     * @param division the name of the Division to search for teams by
     * @return all Teams in the specified Division
     */
    Flux<Team> findAllByDivision(String division);
}
