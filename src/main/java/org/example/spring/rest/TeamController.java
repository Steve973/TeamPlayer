package org.example.spring.rest;

import org.example.spring.data.team.TeamRepository;
import org.example.spring.data.validation.TeamValidator;
import org.example.spring.model.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

/**
 * This class serves as the {@link RestController} for serving up Team resources.
 */
@RestController
public class TeamController {
    private TeamRepository teamRepository;

    public TeamController(@Autowired TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

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
    public Team getByName(@RequestParam(value = "name") String name) {
        return teamRepository.findByName(name);
    }

    /**
     * Get the Team with the given key.
     *
     * @param key the key of the Team to find
     * @return the Team with the given key
     */
    @RequestMapping(value = "/teams/byKey", method = RequestMethod.GET)
    public Team getByKey(@RequestParam(value = "key") String key) {
        return teamRepository.findByKey(key);
    }

    /**
     * Get all of the Teams that play in the given conference.
     *
     * @param conference the conference to find all participating Teams
     * @return all Teams that play in the given conference
     */
    @RequestMapping(value = "/teams/byConference", method = RequestMethod.GET)
    public Page<Team> getAllByConference(@RequestParam(value = "conference") String conference, Pageable pageable) {
        return teamRepository.findAllByConference(conference, pageable);
    }

    /**
     * Get all of the Teams that play in the given division.
     *
     * @param division the division to find all participating Teams
     * @return all Teams that play in the given division
     */
    @RequestMapping(value = "/teams/byDivision", method = RequestMethod.GET)
    public Page<Team> getAllByDivsion(@RequestParam(value = "division") String division, Pageable pageable) {
        return teamRepository.findAllByDivision(division, pageable);
    }

    @RequestMapping(value = "/teams", method = RequestMethod.POST)
    public Team createTeam(@Valid @RequestBody Team team) {
        return teamRepository.insert(team);
    }

    @RequestMapping(value = "/teams", method = RequestMethod.PUT)
    public Team updateTeam(@Valid @RequestBody Team team) {
        return teamRepository.save(team);
    }

    @RequestMapping(value = "/teams", method = RequestMethod.DELETE)
    public void deleteTeam(@Valid @RequestBody Team team) {
        teamRepository.delete(team);
    }

    @InitBinder("team")
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(new TeamValidator());
    }
}
