package org.example.spring.data.athlete;

import org.example.spring.model.Athlete;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * This is the Repository interface for the Athlete objects.
 */
@Repository
public interface AthleteRepository extends MongoRepository<Athlete, String> {

    /**
     * Finds all Athletes by name.
     *
     * @param name The name to search Athletes by
     * @return the Athlete with the given name
     */
    Athlete findByName(String name);

    /**
     * Finds all Athletes by Team name.
     *
     * @param team Team name to use to search for Athletes
     * @param pageable Gets paging and search information from the parameters
     * @return all Athletes associated with the given Team
     */
    Page<Athlete> findAllByPositionTeamName(String team, Pageable pageable);

    /**
     * Find all Athletes that have the given Position.
     *
     * @param position the Position name to search for Athletes by
     * @param pageable Gets paging and search information from the parameters
     * @return all Athletes associated with the given Position
     */
    Page<Athlete> findAllByPositionName(String position, Pageable pageable);

    /**
     * Finds all Athletes in the given Conference.
     *
     * @param conference the name of the Conference to search for Athletes by
     * @param pageable Gets paging and search information from the parameters
     * @return all Athletes associated with the given Conference
     */
    Page<Athlete> findAllByPositionTeamConference(String conference, Pageable pageable);

    /**
     * Finds all Athletes in the given Division.
     *
     * @param division the name of the Division to search for Athletes by
     * @param pageable Gets paging and search information from the parameters
     * @return all Athletes associated with the given Division
     */
    Page<Athlete> findAllByPositionTeamDivision(String division, Pageable pageable);
}
