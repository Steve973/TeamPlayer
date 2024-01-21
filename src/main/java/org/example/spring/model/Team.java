package org.example.spring.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * This is the model object that represents a team.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Document
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Team extends PersistableEntity {

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
