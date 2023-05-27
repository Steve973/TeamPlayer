package org.example.spring.rest;

import org.example.spring.data.team.TeamRepository;
import org.example.spring.data.validation.TeamValidator;
import org.example.spring.model.Team;
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
    private final TeamRepository teamRepository;

    public TeamController(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    /**
     * Get all Teams in the data store.
     *
     * @return all Teams in the data store
     */
    @GetMapping(value = "/teams")
    public Collection<Team> getAllTeams() {
        return teamRepository.findAll();
    }

    /**
     * Get the Team with the given name.
     *
     * @param name The name of the Team to find
     * @return the Team with the given name
     */
    @GetMapping(value = "/teams/byName")
    public Team getByName(@RequestParam(value = "name") String name) {
        return teamRepository.findByName(name);
    }

    /**
     * Get the Team with the given key.
     *
     * @param key the key of the Team to find
     * @return the Team with the given key
     */
    @GetMapping(value = "/teams/byKey")
    public Team getByKey(@RequestParam(value = "key") String key) {
        return teamRepository.findByKey(key);
    }

    /**
     * Get all Teams that play in the given conference.
     *
     * @param conference the conference to find all participating Teams
     * @return all Teams that play in the given conference
     */
    @GetMapping(value = "/teams/byConference")
    public Page<Team> getAllByConference(@RequestParam(value = "conference") String conference, Pageable pageable) {
        return teamRepository.findAllByConference(conference, pageable);
    }

    /**
     * Get all Teams that play in the given division.
     *
     * @param division the division to find all participating Teams
     * @return all Teams that play in the given division
     */
    @GetMapping(value = "/teams/byDivision")
    public Page<Team> getAllByDivsion(@RequestParam(value = "division") String division, Pageable pageable) {
        return teamRepository.findAllByDivision(division, pageable);
    }

    /**
     * Inserts a new Team.
     *
     * @param team the Team to insert
     * @return the inserted Team
     */
    @PostMapping(value = "/teams")
    public Team createTeam(@Valid @RequestBody Team team) {
        return teamRepository.insert(team);
    }

    /**
     * Updates an existing Team.
     *
     * @param team the Team to update
     * @return the updated Team
     */
    @PutMapping(value = "/teams")
    public Team updateTeam(@Valid @RequestBody Team team) {
        return teamRepository.save(team);
    }

    /**
     * Deletes a Team.
     *
     * @param team the Team to delete
     */
    @DeleteMapping(value = "/teams")
    public void deleteTeam(@Valid @RequestBody Team team) {
        teamRepository.delete(team);
    }

    /**
     * Adds validation for doing CRUD operations on Athletes.
     *
     * @param binder the Binder to add validators to
     */
    @InitBinder("team")
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(new TeamValidator());
    }
}
