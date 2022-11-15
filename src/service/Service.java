package service;

import domain.Friendship;
import domain.User;

import java.time.LocalDateTime;
import java.util.*;

import repo.file.UserFileRepo;
import repo.memory.FriendshipMemoryRepo;
import validators.Validator;

/**
 * Service class
 */
public class Service {
    private final Validator<User> validator;
    //private RepoMemoryUser repo;
    private UserFileRepo repo;
    private FriendshipMemoryRepo friendships;

    public Service(Validator<User> validator, UserFileRepo repo, FriendshipMemoryRepo friendships) {
        this.validator = validator;
        this.repo = repo;
        this.friendships = friendships;
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
        System.out.println(user.getID());
    }

    /**
     * Deletes a user based on ID
     * @param ID int
     */
    public void deleteUserService(int ID) {
        for (User u : repo.getAll()) {
            for (User fr : u.getFriends()) {
                if (fr.getID() == ID) {
                    u.getFriends().remove(fr);
                    break;
                }
            }
        }
        for(User u: repo.getAll()){
            if(u.getID() == ID){
                repo.delete(u);
            }
        }
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
        friendships.save(new Friendship(found1.getID(), found2.getID(), LocalDateTime.now()));

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
        friendships.delete(new Friendship(found1.getID(), found2.getID(), LocalDateTime.now()));
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
}
