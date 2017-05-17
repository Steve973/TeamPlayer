package org.example.spring.rest;

import org.example.spring.data.TeamRepository;
import org.example.spring.model.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

/**
 * This class serves as the {@link RestController} for serving up Team resources.
 */
@RestController
public class TeamController {
    @Autowired
    private TeamRepository teamRepository;

    /**
     * Get all Teams in the data store.
     *
     * @return all Teams in the data store
     */
    @RequestMapping(value = "/teams", method = RequestMethod.GET)
    public Collection<Team> getAllTeams() {
        return teamRepository.findAll();
    }

    /**
     * Get the Team with the given name.
     *
     * @param name The name of the Team to find
     * @return the Team with the given name
     */
    @RequestMapping(value = "/teams/byName", method = RequestMethod.GET)
    public Team getByName(@RequestParam(value="name") String name) {
        return teamRepository.findByName(name);
    }

    /**
     * Get the Team with the given key.
     *
     * @param key the key of the Team to find
     * @return the Team with the given key
     */
    @RequestMapping(value = "/teams/byKey", method = RequestMethod.GET)
    public Team getByKey(@RequestParam(value="key") String key) {
        return teamRepository.findByKey(key);
    }

    /**
     * Get all of the Teams that play in the given conference.
     *
     * @param conference the conference to find all participating Teams
     * @return all Teams that play in the given conference
     */
    @RequestMapping(value = "/teams/byConference", method = RequestMethod.GET)
    public Collection<Team> getAllByConference(@RequestParam(value="conference") String conference) {
        return teamRepository.findAllByConference(conference);
    }

    /**
     * Get all of the Teams that play in the given division.
     *
     * @param division the division to find all participating Teams
     * @return all Teams that play in the given division
     */
    @RequestMapping(value = "/teams/byDivision", method = RequestMethod.GET)
    public Collection<Team> getAllByDivsion(@RequestParam(value="division") String division) {
        return teamRepository.findAllByDivision(division);
    }
}
