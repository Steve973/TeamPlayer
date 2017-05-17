package org.example.spring.data;

import org.example.spring.model.Athlete;

/**
 * This is the Repository interface for the Athlete objects.
 */
public interface AthleteRepository extends AbstractTeamPlayerRepository<Athlete, String> {
    Athlete findByName(String name);
}
