package org.example.spring.model;

import javax.validation.Valid;
import java.util.Objects;

/**
 * This is the model object that represents a position, which is an {@link Athlete}'s role on a {@link Team}.
 */
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

    public Position() {
    }

    public String getJersey() {
        return jersey;
    }

    public void setJersey(String jersey) {
        this.jersey = jersey;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    @Override
    public boolean equals(Object obj) {
        if (!Position.class.isInstance(obj)) {
            return false;
        }

        Position otherPosition = Position.class.cast(obj);

        return Objects.equals(otherPosition.team, team)
                && otherPosition.name.equals(name)
                && Objects.equals(otherPosition.jersey, jersey);
    }
}
