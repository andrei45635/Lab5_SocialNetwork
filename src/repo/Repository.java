
package repo;

import domain.Entity;

import java.util.List;

/**
 * Repository CRUD
 */
public interface Repository<ID, E extends Entity<ID>> {
    /**
     * Returns the list of all Entities
     * @return List of Entities
     */
    List<E> getAll();

    /**
     * Deletes an entity
     * @param entity E
     * @return true if the deletion was successful, false otherwise
     */
     boolean delete(E entity);

    /**
     * Updates an entity
     * @param entity E
     * @return the entity if it doesn't exist
     */
    E update (E entity);

    /**
     * Saves and validates an entity if it isn't already saved, throws Illegal Argument Exception otherwise
     * @param entity E
     * @return entity E if it is already logged in
     */
    E save(E entity);
}