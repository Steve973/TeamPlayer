package org.example.spring.data;

import org.example.spring.model.Team;

import java.util.Set;

/**
 * This is the Repository interface for the Team objects.
 */
public interface TeamRepository extends AbstractTeamPlayerRepository<Team, String> {
    Team findByKey(String key);
    Set<Team> findAllByConference(String conference);
    Set<Team> findAllByDivision(String division);
}
