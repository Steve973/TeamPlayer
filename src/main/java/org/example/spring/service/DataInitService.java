package org.example.spring.service;

import lombok.extern.slf4j.Slf4j;
import org.example.spring.data.athlete.AthleteRepository;
import org.example.spring.data.team.TeamRepository;
import org.example.spring.model.Athlete;
import org.example.spring.model.Team;
import org.example.spring.util.DataUtil;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Set;

@Service
@Slf4j
public class DataInitService {

    private final AthleteRepository athleteRepository;

    private final TeamRepository teamRepository;

    public DataInitService(AthleteRepository athleteRepository, TeamRepository teamRepository) {
        this.athleteRepository = athleteRepository;
        this.teamRepository = teamRepository;
    }

    public String init() throws IOException {
        athleteRepository.deleteAll();
        teamRepository.deleteAll();

        Set<Team> teams = DataUtil.getTeams();
        teamRepository.insert(teams);

        Set<Athlete> athletes = DataUtil.getAthletes();
        athleteRepository.insert(athletes);

        return "Saved " + teams.size() + " teams and " + athletes.size() + " athletes";
    }
}
