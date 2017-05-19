package org.example.spring.data.team;

import org.example.spring.data.AbstractTeamPlayerRepository;
import org.example.spring.model.Team;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

/**
 * This is the Repository interface for the Team objects.
 */
@Repository
public interface TeamRepository extends AbstractTeamPlayerRepository<Team, String> {
    /**
     * Teams have a unique key, like "PHI" for the Philadelphia Flyers.
     * This finds the team associated with the given key.
     *
     * @param key a unique three letter key for a Team
     * @return the Team associated with the given key
     */
    Team findByKey(String key);

    /**
     * Find all Teams by the given Conference.
     *
     * @param conference the name of the Conference to search for teams by
     * @param pageable  Gets paging and search information from the parameters
     * @return all Teams in the specified Conference
     */
    Page<Team> findAllByConference(String conference, Pageable pageable);

    /**
     * Find all teams by the given Division.
     *
     * @param division the name of the Division to search for teams by
     * @param pageable Gets paging and search information from the parameters
     * @return all Teams in the specified Division
     */
    Page<Team> findAllByDivision(String division, Pageable pageable);
}
