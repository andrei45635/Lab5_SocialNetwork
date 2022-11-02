package repo.memory;

import domain.User;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import repo.Repository;
import validators.Validator;

public class RepoMemory implements Repository {
    private final Validator<User> validator;
    private List<User> users;

    public RepoMemory(Validator<User> validator) {
        this.validator = validator;
        this.users = new ArrayList<>();
    }

    public List<User> getAllUsers() {
        return this.users;
    }

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

    public void addUser(User user) {
        for (User u : users) {
            if (u.getID() == user.getID()) {
                throw new IllegalArgumentException("This user is already logged in\n");
            }
        }
        validator.validate(user);
        users.add(user);
    }

    public void addFriend(User entity1, User entity2) {
        validator.validate(entity1);
        validator.validate(entity2);
        entity1.getFriends().add(entity2);
        entity2.getFriends().add(entity1);
    }

    public void deleteFriend(User entity1, User entity2) {
        validator.validate(entity1);
        validator.validate(entity2);
        entity1.getFriends().remove(entity2);
        entity2.getFriends().remove(entity1);
    }
}
