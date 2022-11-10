package service;

import domain.User;

import java.util.*;

import repo.file.UserFileRepo;
import repo.memory.RepoMemoryUser;
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
        return repo.getAllUsers();
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
        repo.addUser(user);
    }

    /**
     * Deletes a user based on ID
     *
     * @param ID int
     */
    public void deleteUserService(int ID) {
        repo.deleteUser(ID);
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
        for (User u : repo.getAllUsers()) {
            if (u.getID() == ID) {
                found1 = u;
            }
        }

        for (User u : repo.getAllUsers()) {
            if (u.getID() == ID2) {
                found2 = u;
            }
        }
        assert found1 != null;

        this.repo.addFriend(found1, found2);
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
        for (User u : repo.getAllUsers()) {
            if (u.getID() == ID) {
                found1 = u;
            }
        }

        for (User u : repo.getAllUsers()) {
            if (u.getID() == ID2) {
                found2 = u;
            }
        }

        assert found1 != null;

        this.repo.deleteFriend(found1, found2);
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
        List<User> copy = new ArrayList<>(repo.getAllUsers());
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

    public int longestPath (){
        int longestPath = 0;
        List<User> users = new ArrayList<>(repo.getAllUsers());
        for(User u: users){
            for(User fr: u.getFriends()){
                if(users.contains(fr)){
                    users.remove(fr);
                }
            }
            longestPath = Math.max(longestPath, DFS_util(u));
        }
        return longestPath;
    }
}

/*
    public List<int[]> IDs() {
        List<int[]> res = new ArrayList<>();
        Iterator var2 = this.repo.getAllUsers().iterator();

        while (var2.hasNext()) {
            User u = (User) var2.next();
            List<User> friends = u.getFriends();
            Iterator var5 = friends.iterator();

            while (var5.hasNext()) {
                User fr = (User) var5.next();
                if (fr.getID() != u.getID()) {
                    res.add(new int[]{u.getID(), fr.getID()});
                }
            }
        }

        return res;
    }

    public void DFS_Util(boolean[] visited, int vertex) {
        visited[vertex] = true;
        List<int[]> ids = this.IDs();

        for (int k = 0; k < ids.get(vertex).length; ++k) {
            if (!visited[k]) {
                this.DFS_Util(visited, k);
            }
        }

    }

    public int cConex() {
        int conexe = 0;
        List<int[]> ids = this.IDs();
        boolean[] visited = new boolean[ids.size()];

        for (int v = 0; v < ids.size(); ++v) {
            this.DFS_Util(visited, v);
            conexe++;
        }

        return conexe / 2;
    }
}
*/
