package org.example.spring.rest;

import org.example.spring.data.athlete.AthleteRepository;
import org.example.spring.data.validation.AthleteValidator;
import org.example.spring.model.Athlete;
import org.example.spring.model.Position;
import org.example.spring.model.Team;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * This class serves as the {@link RestController} for serving up Athlete resources and provides
 * endpoints to retrieve Athletes by various properties or aspects that may apply to a subset of
 * all Athletes in the data store.
 */
@RestController
public class AthleteController {

    private final AthleteRepository athleteRepository;

    public AthleteController(AthleteRepository athleteRepository) {
        this.athleteRepository = athleteRepository;
    }

    /**
     * Get all Athletes in the data store.
     *
     * @return a Collection of all Athletes in the data store
     */
    @GetMapping(value = "/athletes")
    public Page<Athlete> getAllAthletes(Pageable pageable) {
        return athleteRepository.findAll(pageable);
    }

    /**
     * Get an Athlete by name.
     *
     * @return a Collection of all Athletes in the data store
     */
    @GetMapping(value = "/athletes/byName")
    public Athlete getAthleteByName(String name) {
        return athleteRepository.findByName(name);
    }

    /**
     * Get all Athletes for the given {@link Team}.
     *
     * @param teamName The name of the Team to retrieve all associated Athletes
     * @return all Athletes associated with the given {@link Team}.
     */
    @GetMapping(value = "/athletes/byTeam")
    public Page<Athlete> getAllAthletesByTeamName(@RequestParam(value = "name") String teamName, Pageable pageable) {
        return athleteRepository.findAllByPositionTeamName(teamName, pageable);
    }

    /**
     * Get all Athletes that play at the given {@link Position}.
     *
     * @param positionName The name of the {@link Position} to retrieve all Athletes that have that position
     * @return all Athletes with the given {@link Position}
     */

    @GetMapping(value = "/athletes/byPosition")
    public Page<Athlete> getAllAthletesByPosition(@RequestParam(value = "name") String positionName, Pageable pageable) {
        return athleteRepository.findAllByPositionName(positionName, pageable);
    }

    /**
     * Get all Athletes that play in the given conference.
     *
     * @param conference the conference name to find all associated Athletes
     * @return all Athletes that play in the given conference
     */

    @GetMapping(value = "/athletes/byConference")
    public Page<Athlete> getAllByConference(@RequestParam(value = "conference") String conference, Pageable pageable) {
        return athleteRepository.findAllByPositionTeamConference(conference, pageable);
    }

    /**
     * Get all Athletes that play in the given division.
     *
     * @param division the division name to find all associated Athletes
     * @return all Athletes that play in the given division
     */
    @GetMapping(value = "/athletes/byDivision")
    public Page<Athlete> getAllByDivsion(@RequestParam(value = "division") String division, Pageable pageable) {
        return athleteRepository.findAllByPositionTeamDivision(division, pageable);
    }

    /**
     * Persists a new Athlete.
     *
     * @param athlete the Athlete to insert
     * @return the inserted Athlete
     */
    @PostMapping(value = "/athletes")
    public Athlete createAthlete(@Valid @RequestBody Athlete athlete) {
        return athleteRepository.insert(athlete);
    }

    /**
     * Updates an existing Athlete.
     *
     * @param athlete the Athlete to update
     * @return the updated Athlete
     */
    @PutMapping(value = "/athletes")
    public Athlete updateAthlete(@Valid @RequestBody Athlete athlete) {
        return athleteRepository.save(athlete);
    }

    /**
     * Deletes an Athlete.
     *
     * @param athlete the Athlete to delete
     */
    @DeleteMapping(value = "/athletes")
    public void deleteAthlete(@Valid @RequestBody Athlete athlete) {
        athleteRepository.delete(athlete);
    }

    /**
     * Adds validation for doing CRUD operations on Athletes.
     *
     * @param binder the Binder to add validators to
     */
    @InitBinder("athlete")
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(new AthleteValidator());
    }
}
