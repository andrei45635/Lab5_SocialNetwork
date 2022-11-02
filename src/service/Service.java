package service;

import domain.User;

import java.util.*;

import repo.memory.RepoMemory;
import validators.Validator;

public class Service {
    private final Validator<User> validator;
    private RepoMemory repo;

    public Service(Validator<User> validator, RepoMemory repo) {
        this.validator = validator;
        this.repo = repo;
    }

    public List<User> getAllService() {
        return this.repo.getAllUsers();
    }

    public void addUserService(int ID, String firstName, String lastName, String email, String passwd, int age) {
        User user = new User(ID, firstName, lastName, email, passwd, age);
        validator.validate(user);
        repo.addUser(user);
    }

    public void deleteUserService(int ID) {
        repo.deleteUser(ID);
    }

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

    public void BFS(List<User> copy) {
        Queue<User> q = new LinkedList<>();
        q.add(copy.remove(0));
        while (!q.isEmpty()) {
            User user = q.remove();
            for (User fr : user.getFriends()) {
                if (copy.contains(fr)) {
                    q.add(fr);
                }
            }
            copy.remove(user);
        }
    }

    public void DFS(List<User> copy){
        Stack<User> s = new Stack<>();
        s.push(copy.remove(0));
        while(!s.isEmpty()){
            User user = s.pop();
            for(User fr: user.getFriends()){
                if(copy.contains(fr)){
                    s.push(fr);
                }
            }
            copy.remove(user);
        }
    }
    public int connectedCommunities() {
        int connected = 0;
        List<User> copy = new ArrayList<>(repo.getAllUsers());
        while(!copy.isEmpty()){
            //BFS(copy);
            DFS(copy);
            connected++;
        }
        return connected;
    }


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
