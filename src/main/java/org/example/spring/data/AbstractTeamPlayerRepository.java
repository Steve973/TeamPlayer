package org.example.spring.data;

import org.example.spring.model.AbstractTeamPlayerEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.io.Serializable;

/**
 * Abstract repository interface for the app so that the database can be easily
 * switched out by only changing it here, and then the extending interfaces do
 * not have to be modified.
 */
public interface AbstractTeamPlayerRepository<T extends AbstractTeamPlayerEntity, U extends Serializable> extends MongoRepository<T, U> {
    T findByName(String name);
}
