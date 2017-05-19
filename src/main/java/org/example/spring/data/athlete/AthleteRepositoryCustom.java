package org.example.spring.data.athlete;

import org.example.spring.model.Athlete;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Defines custom ways of querying for Athletes, like using aggregation/pipelines.
 */
public interface AthleteRepositoryCustom {
    /**
     * Finds all Athletes by Team name.
     *
     * @param team Team name to use to search for Athletes
     * @param pageable Gets paging and search information from the parameters
     * @return all Athletes associated with the given Team
     */
    Page<Athlete> findAllByTeam(String team, Pageable pageable);

    /**
     * Find all Athletes that have the given Position.
     *
     * @param position the Position name to search for Athletes by
     * @param pageable Gets paging and search information from the parameters
     * @return all Athletes associated with the given Position
     */
    Page<Athlete> findAllByPosition(String position, Pageable pageable);

    /**
     * Finds all Athletes in the given Conference.
     *
     * @param conference the name of the Conference to search for Athletes by
     * @param pageable Gets paging and search information from the parameters
     * @return all Athletes associated with the given Conference
     */
    Page<Athlete> findAllByConference(String conference, Pageable pageable);

    /**
     * Finds all Athletes in the given Division.
     *
     * @param division the name of the Division to search for Athletes by
     * @param pageable Gets paging and search information from the parameters
     * @return all Athletes associated with the given Division
     */
    Page<Athlete> findAllByDivision(String division, Pageable pageable);
}
