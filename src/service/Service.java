package service;

import domain.Friendship;
import domain.User;

import java.time.LocalDateTime;
import java.util.*;

import repo.file.FriendshipFileRepo;
import repo.file.UserFileRepo;
import validators.Validator;

/**
 * Service class
 */
public class Service {
    private final Validator<User> validator;
    //private RepoMemoryUser repo;
    private UserFileRepo repo;
    private FriendshipFileRepo friendships;
    //private FriendshipMemoryRepo friendships;

    public Service(Validator<User> validator, UserFileRepo repo, FriendshipFileRepo friendships) {
        this.validator = validator;
        this.repo = repo;
        this.friendships = friendships;
        addFriendstoUsers();
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
     * Returns a list with all the friends
     *
     * @return List of Friends
     */
    public List<Friendship> getAllFriendsService() {
        return friendships.getAll();
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
     *
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
        repo.delete(new User(ID, "a", "b", "a.com", "abcdefgha", 20));
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

        for (Friendship fr : friendships.getAll()) {
            if (fr.getIdU1() == found1.getID() && fr.getIdU2() == found2.getID()) {
                throw new IllegalArgumentException("These users are already friends!\n");
            }
        }

        assert found1 != null;
        found1.getFriends().add(found2);
        assert found2 != null;
        found2.getFriends().add(found1);
        friendships.save(new Friendship(found1.getID(), found2.getID(), LocalDateTime.now()));
    }

    /**
     * Loads the data from the friends.csv file and adds them to the friends list of each user in the repo
     * Please note that this is a WIP, this is NOT the final product
     */
    public void addFriendstoUsers() {
        for (User u : repo.getAll()) {
            for (Friendship fr : friendships.getAll()) {
                if (u.getID() == fr.getIdU1()) {
                    for (User u2 : repo.getAll()) {
                        if (u2.getID() == fr.getIdU2()) {
                            u.getFriends().add(u2);
                        }
                    }
                } else if (u.getID() == fr.getIdU2()) {
                    for (User u2 : repo.getAll()) {
                        if (u2.getID() == fr.getIdU1()) {
                            u.getFriends().add(u2);
                        }
                    }
                }
            }
        }
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
     *
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
     *
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
