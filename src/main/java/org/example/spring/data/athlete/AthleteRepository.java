package org.example.spring.data.athlete;

import org.example.spring.data.AbstractTeamPlayerRepository;
import org.example.spring.model.Athlete;
import org.springframework.stereotype.Repository;

/**
 * This is the Repository interface for the Athlete objects.
 */
@Repository
public interface AthleteRepository extends AbstractTeamPlayerRepository<Athlete, String>, AthleteRepositoryCustom {
    Athlete findByName(String name);
}
