package org.example.spring.model;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * This is the model object that represents a team.
 */
@Document
public class Team extends AbstractTeamPlayerEntity {
    private String key;
    private String conference;
    private String division;

    public Team() {
    }

    public Team(final String name, final String key, final String conference, final String division) {
        this.name = name;
        this.key = key;
        this.conference = conference;
        this.division = division;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getConference() {
        return conference;
    }

    public void setConference(String conference) {
        this.conference = conference;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }
}
