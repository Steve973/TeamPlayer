package org.example.spring.rest;

import org.example.spring.data.AthleteRepository;
import org.example.spring.data.PositionRepository;
import org.example.spring.data.TeamRepository;
import org.example.spring.model.Athlete;
import org.example.spring.model.Position;
import org.example.spring.model.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * This class serves as the {@link RestController} for serving up Athlete resources and provides
 * endpoints to retrieve Athletes by various properties or aspects that may apply to a subset of
 * all Athletes in the data store.
 */
@RestController
public class AthleteController {
    @Autowired
    private AthleteRepository athleteRepository;
    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private PositionRepository positionRepository;

    /**
     * Get all Athletes in the data store.
     *
     * @return a Collection of all Athletes in the data store
     */
    @RequestMapping(value = "/athletes", method = RequestMethod.GET)
    public Collection<Athlete> getAllAthletes() {
        return athleteRepository.findAll();
    }

    /**
     * Get all Athletes for the given {@link Team}.
     *
     * @param teamName The name of the Team to retrieve all associated Athletes
     * @return all Athletes associated with the given {@link Team}.
     */
    @RequestMapping(value = "/athletes/byTeam", method = RequestMethod.GET)
    public Collection<Athlete> getAllAthletesByTeam(@RequestParam(value="name") String teamName) {
        Team team = teamRepository.findByName(teamName);
        return positionRepository.findAllByTeam(team)
                .stream()
                .map(Position::getAthlete)
                .collect(Collectors.toSet());
    }

    /**
     * Get all Athletes that play on a {@link Team} at the given {@link Position}.
     *
     * @param positionName The name of the {@link Position} to retrieve all Athletes that have that position
     * @return all Athletes with the given {@link Position}
     */
    @RequestMapping(value = "/athletes/byPosition", method = RequestMethod.GET)
    public Collection<Athlete> getAllAthletesByPosition(@RequestParam(value="name") String positionName) {
        return positionRepository.findAllByName(positionName)
                .stream()
                .map(Position::getAthlete)
                .collect(Collectors.toSet());
    }

    /**
     * Get all Athletes that play in the given conference.
     *
     * @param conference the conference name to find all associated Athletes
     * @return all Athletes that play in the given conference
     */
    @RequestMapping(value = "/athletes/byConference", method = RequestMethod.GET)
    public Collection<Athlete> getAllByConference(@RequestParam(value="conference") String conference) {
        return positionRepository.findAllByName(conference)
                .stream()
                .map(Position::getAthlete)
                .collect(Collectors.toSet());
    }

    /**
     * Get all Athletes that play in the given division.
     *
     * @param division the division name to find all associated Athletes
     * @return all Athletes that play in the given division
     */
    @RequestMapping(value = "/athletes/byDivision", method = RequestMethod.GET)
    public Collection<Athlete> getAllByDivsion(@RequestParam(value="division") String division) {
        return positionRepository.findAllByName(division)
                .stream()
                .map(Position::getAthlete)
                .collect(Collectors.toSet());
    }
}
