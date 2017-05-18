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
    Team findByKey(String key);

    Page<Team> findAllByConference(String conference, Pageable pageable);

    Page<Team> findAllByDivision(String division, Pageable pageable);
}
