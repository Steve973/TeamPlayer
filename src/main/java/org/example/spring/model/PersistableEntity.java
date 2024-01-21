package org.example.spring.model;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

/**
 * Base class for all entities in this app.  All entities will have an ID and a name parameter.
 */
@Data
public abstract class PersistableEntity {

    @MongoId(FieldType.OBJECT_ID)
    protected String id;

    protected String name;
}
