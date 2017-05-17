package org.example.spring.data;

import org.example.spring.model.Team;

/**
 * This is the Repository interface for the Team objects.
 */
public interface TeamRepository extends AbstractTeamPlayerRepository<Team, String> {
}
