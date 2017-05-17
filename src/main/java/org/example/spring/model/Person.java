package org.example.spring.model;

/**
 * This is the model object that represents a person.
 */
public class Person extends AbstractTeamPlayerEntity {
    public Person() {
    }

    public Person(String name) {
        this.name = name;
    }
}
