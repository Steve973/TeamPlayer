package org.example.spring.rest;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.example.spring.data.athlete.AthleteRepository;
import org.example.spring.data.validation.AthleteValidator;
import org.example.spring.model.Athlete;
import org.example.spring.model.Position;
import org.example.spring.model.Team;
import org.springframework.beans.factory.annotation.Autowired;
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
    private AthleteRepository athleteRepository;

    public AthleteController(@Autowired AthleteRepository athleteRepository) {
        this.athleteRepository = athleteRepository;
    }

    /**
     * Get all Athletes in the data store.
     *
     * @return a Collection of all Athletes in the data store
     */
    @RequestMapping(value = "/athletes", method = RequestMethod.GET)
    public Page<Athlete> getAllAthletes(Pageable pageable) {
        return athleteRepository.findAll(pageable);
    }

    /**
     * Get all Athletes for the given {@link Team}.
     *
     * @param teamName The name of the Team to retrieve all associated Athletes
     * @return all Athletes associated with the given {@link Team}.
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "string", paramType = "query",
                    value = "Results page you want to retrieve (0..N)"),
            @ApiImplicitParam(name = "size", dataType = "string", paramType = "query",
                    value = "Number of records per page."),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
                    value = "Sorting criteria in the format: property(,asc|desc). " +
                            "Default sort order is ascending. " +
                            "Multiple sort criteria are supported.")
    })
    @RequestMapping(value = "/athletes/byTeam", method = RequestMethod.GET)
    public Page<Athlete> getAllAthletesByTeam(@RequestParam(value = "name") String teamName, Pageable pageable) {
        return athleteRepository.findAllByTeam(teamName, pageable);
    }

    /**
     * Get all Athletes that play at the given {@link Position}.
     *
     * @param positionName The name of the {@link Position} to retrieve all Athletes that have that position
     * @return all Athletes with the given {@link Position}
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "string", paramType = "query",
                    value = "Results page you want to retrieve (0..N)"),
            @ApiImplicitParam(name = "size", dataType = "string", paramType = "query",
                    value = "Number of records per page."),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
                    value = "Sorting criteria in the format: property(,asc|desc). " +
                            "Default sort order is ascending. " +
                            "Multiple sort criteria are supported.")
    })
    @RequestMapping(value = "/athletes/byPosition", method = RequestMethod.GET)
    public Page<Athlete> getAllAthletesByPosition(@RequestParam(value = "name") String positionName, Pageable pageable) {
        return athleteRepository.findAllByPosition(positionName, pageable);
    }

    /**
     * Get all Athletes that play in the given conference.
     *
     * @param conference the conference name to find all associated Athletes
     * @return all Athletes that play in the given conference
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "string", paramType = "query",
                    value = "Results page you want to retrieve (0..N)"),
            @ApiImplicitParam(name = "size", dataType = "string", paramType = "query",
                    value = "Number of records per page."),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
                    value = "Sorting criteria in the format: property(,asc|desc). " +
                            "Default sort order is ascending. " +
                            "Multiple sort criteria are supported.")
    })
    @RequestMapping(value = "/athletes/byConference", method = RequestMethod.GET)
    public Page<Athlete> getAllByConference(@RequestParam(value = "conference") String conference, Pageable pageable) {
        return athleteRepository.findAllByConference(conference, pageable);
    }

    /**
     * Get all Athletes that play in the given division.
     *
     * @param division the division name to find all associated Athletes
     * @return all Athletes that play in the given division
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "string", paramType = "query",
                    value = "Results page you want to retrieve (0..N)"),
            @ApiImplicitParam(name = "size", dataType = "string", paramType = "query",
                    value = "Number of records per page."),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
                    value = "Sorting criteria in the format: property(,asc|desc). " +
                            "Default sort order is ascending. " +
                            "Multiple sort criteria are supported.")
    })
    @RequestMapping(value = "/athletes/byDivision", method = RequestMethod.GET)
    public Page<Athlete> getAllByDivsion(@RequestParam(value = "division") String division, Pageable pageable) {
        return athleteRepository.findAllByDivision(division, pageable);
    }

    /**
     * Persists a new Athlete.
     *
     * @param athlete the Athlete to insert
     * @return the inserted Athlete
     */
    @RequestMapping(value = "/athletes", method = RequestMethod.POST)
    public Athlete createAthlete(@Valid @RequestBody Athlete athlete) {
        return athleteRepository.insert(athlete);
    }

    /**
     * Updates an existing Athlete.
     *
     * @param athlete the Athlete to update
     * @return the updated Athlete
     */
    @RequestMapping(value = "/athletes", method = RequestMethod.PUT)
    public Athlete updateAthlete(@Valid @RequestBody Athlete athlete) {
        return athleteRepository.save(athlete);
    }

    /**
     * Deletes an Athlete.
     *
     * @param athlete the Athlete to delete
     */
    @RequestMapping(value = "/athletes", method = RequestMethod.DELETE)
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
