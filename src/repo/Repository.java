
package repo;

import domain.Entity;
import domain.User;
import validators.ValidatorException;

import java.util.List;

/**
 * Repository CRUD
 */
public interface Repository<ID, E extends Entity<ID>> {
    /**
     * Returns the list of all users
     * @return List of Users
     */
    List<E> getAll();

    /**
     * Deletes a user based on ID
     * @param ID int, the ID of the user
     */
    void delete(E entity);

    /**
     * Adds a user and validates it
     * @param var1 int, the ID of the user
     * @throws ValidatorException if the ID and age aren't int or if the firstName, lastName, email and passwd aren't strings
     */
    //void add(E var1);

    E update (E entity);
    E save(E entity);
}