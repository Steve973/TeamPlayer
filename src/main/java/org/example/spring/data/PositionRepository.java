package org.example.spring.data;

import org.example.spring.model.Athlete;
import org.example.spring.model.Position;
import org.example.spring.model.Team;

import java.util.Set;

/**
 * This is the repository to handle {@link Position} objects, which represent a {@link Athlete} on a {@link Team}.
 */
public interface PositionRepository extends AbstractTeamPlayerRepository<Position, String> {
    Position findByAthlete(Athlete athlete);
    Set<Position> findAllByTeam(Team team);
    Set<Position> findAllByName(String name);
    Set<Position> findAllByJersey(String jersey);
}
