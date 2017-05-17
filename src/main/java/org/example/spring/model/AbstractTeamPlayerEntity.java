package org.example.spring.model;

import org.springframework.data.annotation.Id;

/**
 * Base class for all entities in this app.  All of the entities will
 * have an ID and a name parameter.
 */
public abstract class AbstractTeamPlayerEntity {
    @Id
    protected String id;
    protected String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
