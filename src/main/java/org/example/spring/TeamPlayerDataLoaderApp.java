package org.example.spring;

import org.example.spring.data.PersonRepository;
import org.example.spring.data.PositionRepository;
import org.example.spring.data.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.repository.CrudRepository;

import java.util.Arrays;

/**
 * This is a command line app to load data from a JSON file and use the repositories
 * to populate the data store.
 *
 * Running this application will delete all entries in the datastore for all
 * {@link org.example.spring.model.Person}, {@link org.example.spring.model.Team},
 * and {@link org.example.spring.model.Position} entities.  Then, based on the
 * arguments that point to JSON document files, it will re-populate the data
 * store with the entities found in those files.
 */
@SpringBootApplication
public class TeamPlayerDataLoaderApp implements CommandLineRunner {
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private PositionRepository positionRepository;

    public static void main(String[] args) {
        SpringApplication.run(TeamPlayerDataLoaderApp.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Arrays.asList(personRepository, teamRepository, positionRepository).forEach(CrudRepository::deleteAll);
    }
}
