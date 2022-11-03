package repo.memory;

import domain.User;

import java.util.ArrayList;
import java.util.List;

import repo.Repository;
import validators.Validator;

/**
 * RepoMemory class
 */
public class RepoMemory implements Repository {
    private final Validator<User> validator;
    private List<User> users;

    public RepoMemory(Validator<User> validator) {
        this.validator = validator;
        this.users = new ArrayList<>();
    }

    /**
     * Returns the list of logged in users
     * @return List of Users
     */
    public List<User> getAllUsers() {
        return this.users;
    }

    /**
     * Deletes a user based on ID
     * @param id int, the ID of the user
     */
    public void deleteUser(int id) {
        for(User u: users){
            for(User fr: u.getFriends()){
                if(fr.getID() == id){
                    u.getFriends().remove(fr);
                    break;
                }
            }
        }
        users.removeIf(u -> u.getID() == id);
    }

    /**
     * Adds a user to the list of users
     * @param user int, the ID of the user
     */
    public void addUser(User user) {
        for (User u : users) {
            if (u.getID() == user.getID()) {
                throw new IllegalArgumentException("This user is already logged in\n");
            }
        }
        validator.validate(user);
        users.add(user);
    }

    /**
     * Entity1 becomes friends with Entity2
     * @param entity1 User
     * @param entity2 User
     */
    public void addFriend(User entity1, User entity2) {
        entity1.getFriends().add(entity2);
        entity2.getFriends().add(entity1);
    }

    /**
     * Entity1 breaks his friendship with Entity2
     * @param entity1 User
     * @param entity2 User
     */
    public void deleteFriend(User entity1, User entity2) {
        entity1.getFriends().remove(entity2);
        entity2.getFriends().remove(entity1);
    }
}
