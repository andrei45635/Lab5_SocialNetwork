package service;

import domain.User;

import java.util.*;

import repo.file.UserFileRepo;
import repo.memory.AbstractMemoryRepo;
import validators.Validator;

/**
 * Service class
 */
public class Service {
    private final Validator<User> validator;
    //private RepoMemoryUser repo;
    private UserFileRepo repo;

    public Service(Validator<User> validator, UserFileRepo repo) {
        this.validator = validator;
        this.repo = repo;
    }

    /**
     * Returns a list with all the users
     *
     * @return List of Users
     */
    public List<User> getAllService() {
        return repo.getAll();
    }

    /**
     * Creates a user and adds it to the list of users
     *
     * @param ID        int
     * @param firstName String
     * @param lastName  String
     * @param email     String
     * @param passwd    String
     * @param age       int
     */
    public void addUserService(int ID, String firstName, String lastName, String email, String passwd, int age) {
        User user = new User(ID, firstName, lastName, email, passwd, age);
        validator.validate(user);
        repo.save(user);
    }

    /**
     * Deletes a user based on ID
     * @param ID int
     */
    public void deleteUserService(int ID) {
        repo.delete(ID);
    }

    /**
     * The user with the ID ID becomes friends with the user with the ID ID2
     *
     * @param ID  int, user 1
     * @param ID2 int, user 2
     */
    public void addFriendService(int ID, int ID2) {
        User found1 = null;
        User found2 = null;
        for (User u : repo.getAll()) {
            if (u.getID() == ID) {
                found1 = u;
            }
        }

        for (User u : repo.getAll()) {
            if (u.getID() == ID2) {
                found2 = u;
            }
        }
        assert found1 != null;
        found1.getFriends().add(found2);
        assert found2 != null;
        found2.getFriends().add(found1);
    }

    /**
     * The user with the ID ID removes his friendship with the user with the ID ID2
     *
     * @param ID   int, user 1
     * @param ID2, int user 2
     */
    public void deleteFriendService(int ID, int ID2) {
        User found1 = null;
        User found2 = null;
        for (User u : repo.getAll()) {
            if (u.getID() == ID) {
                found1 = u;
            }
        }

        for (User u : repo.getAll()) {
            if (u.getID() == ID2) {
                found2 = u;
            }
        }

        assert found1 != null;
        found1.getFriends().remove(found2);
        assert found2 != null;
        found2.getFriends().remove(found1);
    }

    /**
     * DFS on a copy of the List of users
     * @param copy List of users
     */
    public void DFS(List<User> copy) {
        Stack<User> s = new Stack<>();
        s.push(copy.remove(0));
        while (!s.isEmpty()) {
            User user = s.pop();
            for (User fr : user.getFriends()) {
                if (copy.contains(fr)) {
                    s.push(fr);
                }
            }
            copy.remove(user);
        }
    }

    /**
     * Returns the number of connected components in the network
     * @return int, the number of connected components
     */
    public int connectedCommunities() {
        int connected = 0;
        List<User> copy = new ArrayList<>(repo.getAll());
        while (!copy.isEmpty()) {
            DFS(copy);
            connected++;
        }
        return connected;
    }

    public int DFS_util(User u){
        int longestPath = 0;
        for(User fr: u.getFriends()){
            longestPath = Math.max(longestPath, DFS_util(fr) + 1);
        }
        return longestPath;
    }
}
