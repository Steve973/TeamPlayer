package org.example.spring.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

/**
 * This is the model object that represents a team.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Document
@NoArgsConstructor
public class Team extends AbstractTeamPlayerEntity {

    private String key;

    private String conference;

    private String division;

    public Team(final String name, final String key, final String conference, final String division) {
        this.name = name;
        this.key = key;
        this.conference = conference;
        this.division = division;
    }
}
