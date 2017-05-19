package org.example.spring.data;

import org.example.spring.model.AbstractTeamPlayerEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.io.Serializable;

/**
 * Abstract repository interface for the app so that the database can be easily
 * switched out by only changing it here, and then the extending interfaces do
 * not have to be modified.
 */
public interface AbstractTeamPlayerRepository<T extends AbstractTeamPlayerEntity, U extends Serializable>
        extends PagingAndSortingRepository<T, U>, MongoRepository<T, U> {
    /**
     * All entities have a name field, so provide all extension classes with this ability
     * to find by name.
     *
     * @param name the name to search for the entity by
     * @return the entity associated with the given name
     */
    T findByName(String name);
}
