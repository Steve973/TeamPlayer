package org.example.spring.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * This is the model object that represents an athlete that can have a {@link Position} on a {@link Team}.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@Document
public class Athlete extends AbstractTeamPlayerEntity {

    private Position position;

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
}
