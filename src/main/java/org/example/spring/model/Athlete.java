package org.example.spring.model;

/**
 * This is the model object that represents an athlete that can have a {@link Position} on a {@link Team}.
 */
public class Athlete extends AbstractTeamPlayerEntity {
    public Athlete() {
    }

    public Athlete(String name) {
        this.name = name;
    }
}
