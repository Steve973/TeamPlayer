package org.example.spring.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.Valid;

/**
 * This is the model object that represents a position, which is an {@link Athlete}'s role on a {@link Team}.
 */
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
public class Position extends AbstractTeamPlayerEntity {

    @Valid
    private Team team;

    @Valid
    private String jersey;

    public Position(Team team, String name, String jersey) {
        this.team = team;
        this.name = name;
        this.jersey = jersey;
    }
}
