package org.example.spring.model;

import lombok.Data;
import org.springframework.data.annotation.Id;

/**
 * Base class for all entities in this app.  All entities will
 * have an ID and a name parameter.
 */
@Data
public abstract class AbstractTeamPlayerEntity {

    @Id
    protected String id;

    protected String name;
}
