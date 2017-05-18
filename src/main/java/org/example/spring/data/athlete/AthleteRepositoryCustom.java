package org.example.spring.data.athlete;

import org.example.spring.model.Athlete;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Defines custom ways of querying for Athletes, like using aggregation/pipelines.
 */
public interface AthleteRepositoryCustom {
    Page<Athlete> findAllByTeam(String team, Pageable pageable);

    Page<Athlete> findAllByPosition(String position, Pageable pageable);

    Page<Athlete> findAllByConference(String conference, Pageable pageable);

    Page<Athlete> findAllByDivision(String conference, Pageable pageable);
}
