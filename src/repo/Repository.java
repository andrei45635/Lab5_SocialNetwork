
package repo;

import domain.User;
import validators.ValidatorException;

import java.util.List;

/**
 * Repository CRUD
 */
public interface Repository {
    /**
     * Returns the list of all users
     * @return List of Users
     */
    List<User> getAllUsers();

    /**
     * Deletes a user based on ID
     * @param var1 int, the ID of the user
     */
    void deleteUser(int var1);

    /**
     * Adds a user and validates it
     * @param var1 int, the ID of the user
     * @throws ValidatorException if the ID and age aren't int or if the firstName, lastName, email and passwd aren't strings
     */
    void addUser(User var1);

    /**
     * User var1 becomes friends with var2
     * @param var1 User
     * @param var2 User
     */
    void addFriend(User var1, User var2);

    /**
     * User var1 breaks his friendship with User var2
     * @param var1 User
     * @param var2 User
     */
    void deleteFriend(User var1, User var2);
}
