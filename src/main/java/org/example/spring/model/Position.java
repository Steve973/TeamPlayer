package org.example.spring.model;

/**
 * This is the model object that represents a position, which is an {@link Athlete} on a {@link Team}.
 */
public class Position extends AbstractTeamPlayerEntity {
    private Athlete athlete;
    private Team team;
    private String jersey;

    public Position(Athlete athlete, Team team, String name, String jersey) {
        this.athlete = athlete;
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

    public Athlete getAthlete() {
        return athlete;
    }

    public void setAthlete(Athlete athlete) {
        this.athlete = athlete;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }
}
