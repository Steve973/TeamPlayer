package org.example.spring.data;

import org.example.spring.model.Person;
import org.example.spring.model.Position;
import org.example.spring.model.Team;

/**
 * This is the repository to handle {@link Position} objects, which represent a {@link Person} on a {@link Team}.
 */
public interface PositionRepository extends AbstractTeamPlayerRepository<Position, String> {
    Position findByPerson(Person person);
    Position findByTeam(Team team);
}
