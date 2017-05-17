package org.example.spring.data;

import org.example.spring.model.Person;

/**
 * This is the Repository interface for the Person objects.
 */
public interface PersonRepository extends AbstractTeamPlayerRepository<Person, String> {
}
