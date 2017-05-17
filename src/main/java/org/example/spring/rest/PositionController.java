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

/**
 * This class serves as the {@link RestController} for serving up Position resources.
 */
@RestController
public class PositionController {
    private AthleteRepository athleteRepository;
    private TeamRepository teamRepository;
    private PositionRepository positionRepository;

    public PositionController(@Autowired AthleteRepository athleteRepository,
                              @Autowired TeamRepository teamRepository,
                              @Autowired PositionRepository positionRepository) {
        this.athleteRepository = athleteRepository;
        this.teamRepository = teamRepository;
        this.positionRepository = positionRepository;
    }

    /**
     * Get all of the Positions in the data store.
     *
     * @return all Positions in the data store
     */
    @RequestMapping(value = "/positions", method = RequestMethod.GET)
    public Collection<Position> getAll() {
        return positionRepository.findAll();
    }

    /**
     * Get the Position for the given {@link Athlete}.
     *
     * @param athleteName the name of the {@link Athlete} to find the position of
     * @return the Position of the given {@link Athlete}
     */
    @RequestMapping(value = "/positions/byAthlete", method = RequestMethod.GET)
    public Position getAllByAthlete(@RequestParam(value = "athlete") String athleteName) {
        Athlete athlete = athleteRepository.findByName(athleteName);
        return positionRepository.findByAthlete(athlete);
    }

    /**
     * Get all of the Positions for the given {@link Team}.
     *
     * @param teamName the name of the team to find all Positions for
     * @return the Positions in the given {@link Team}
     */
    @RequestMapping(value = "/positions/byTeam", method = RequestMethod.GET)
    public Collection<Position> getAllByTeam(@RequestParam(value = "team") String teamName) {
        Team team = teamRepository.findByName(teamName);
        return positionRepository.findAllByTeam(team);
    }

    /**
     * Get all of the Positions by the given Position name from all Teams in all conferences and divisions.
     *
     * @param position the Position name to search for
     * @return all Positions with the given name
     */
    @RequestMapping(value = "/positions/allByName", method = RequestMethod.GET)
    public Collection<Position> getAllByName(@RequestParam(value = "position") String position) {
        return positionRepository.findAllByName(position);
    }

    /**
     * Get one Position associated with the given id.
     *
     * @param id the name of the Position to search for
     * @return a single Position with the given name
     */
    @RequestMapping(value = "/positions/byId", method = RequestMethod.GET)
    public Position getByName(@RequestParam(value = "id") String id) {
        return positionRepository.findOne(id);
    }

    /**
     * Get all of the Positions for the given jersey number.
     *
     * @param jersey the jersey number to search for Positions
     * @return the Positions associated with the given jersey number
     */
    @RequestMapping(value = "/positions/allByJersey", method = RequestMethod.GET)
    public Collection<Position> getAllByJersey(@RequestParam(value = "jersey") String jersey) {
        return positionRepository.findAllByJersey(jersey);
    }
}
