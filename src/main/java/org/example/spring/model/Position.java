package org.example.spring.model;

/**
 * This is the model object that represents a player, which is a {@link Person} on a {@link Team}.
 */
public class Position extends AbstractTeamPlayerEntity {
    private Person person;
    private Team team;
    private String jersey;

    public Position(Person person, Team team, String name, String jersey) {
        this.person = person;
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

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }
}
