package service;

import domain.Friendship;
import domain.User;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import repo.database.FriendshipDBRepo;
import repo.database.UserDBRepo;
import validators.Validator;
import validators.ValidatorException;

/**
 * Service class
 */
public class Service {
    private final Validator<User> validator;
    //private RepoMemoryUser repo;
    //private UserFileRepo repo;
    private UserDBRepo repo;
    private FriendshipDBRepo friendships;
    //private FriendshipFileRepo friendships;
    //private FriendshipMemoryRepo friendships;

    public Service(Validator<User> validator, UserDBRepo repo, FriendshipDBRepo friendships) {
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
    public void addUserService(int ID, String firstName, String lastName, String email, String passwd, int age) throws IOException {
        User user = new User(ID, firstName, lastName, email, passwd, age);
        validator.validate(user);
        try{
            repo.save(user);
        } catch (ValidatorException ve){
            System.out.println(ve.getMessage());
        }
    }

    /**
     * Deletes a user based on ID
     *
     * @param ID int
     */
    public void deleteUserService(int ID) throws IOException {
        for(User u: repo.getAll()){
            for(Friendship fr: friendships.getAll()){
                if((fr.getIdU1() == u.getID() || fr.getIdU2() == u.getID()) && u.getID() == ID){
                    friendships.delete(fr);
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
    public void addFriendService(int ID, int ID2) throws IOException {
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
            assert found1 != null;
            assert found2 != null;
            if (fr.getIdU1() == found1.getID() && fr.getIdU2() == found2.getID()) {
                throw new IllegalArgumentException("These users are already friends!\n");
            }
        }

        assert found1 != null;
        assert found2 != null;
        found1.getFriends().add(found2);
        found2.getFriends().add(found1);
        friendships.save(new Friendship(found1.getID(), found2.getID(), LocalDateTime.now()));
    }

    /**
     * The user with the ID ID removes his friendship with the user with the ID ID2
     *
     * @param ID   int, user 1
     * @param ID2, int user 2
     */
    public void deleteFriendService(int ID, int ID2) throws IOException{
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
