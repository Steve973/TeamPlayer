package org.example.spring.model;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * This is the model object that represents an athlete that can have a {@link Position} on a {@link Team}.
 */
@Document
public class Athlete extends AbstractTeamPlayerEntity {
    private Position position;

    public Athlete() {
    }

    /**
     * Construct an Athlete object, optionally with Position information if the Athlete
     * is on a Team.
     *
     * @param name the name of the Athlete
     * @param position the Position, if the Athlete is on a Team
     */
    public Athlete(String name, Position position) {
        this.name = name;
        this.position = position;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }
}
