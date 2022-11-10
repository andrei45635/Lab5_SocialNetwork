package repo.memory;

import domain.Friendship;
import domain.User;
import validators.Validator;

import java.lang.reflect.GenericArrayType;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class RepoMemoryFriendship extends AbstractMemoryRepo<Friendship>{

    private List<Friendship> friendships;

    public RepoMemoryFriendship() {
        this.friendships = new ArrayList<>();
    }

    public List<Friendship> getAllUsers() {
        return friendships;
    }

    public void addFriend(User u1, User u2) {
        Friendship user1_2 = new Friendship(u1.getID(), u2.getID(), LocalDateTime.now());
        for(Friendship fr: friendships){
            if(fr.getIdU1() == u1.getID() && fr.getIdU2() == u2.getID()){
                throw new IllegalArgumentException("These users are already friends!\n");
            }
        }
        friendships.add(user1_2);
    }

    public void deleteFriend(User u1, User u2) {
        Friendship user1_2 = new Friendship(u1.getID(), u2.getID(), LocalDateTime.now());
        friendships.remove(user1_2);
    }

    @Override
    public void add(Friendship entity) {
        friendships.add(entity);
    }

    @Override
    public void delete(int id) {
        friendships.removeIf(fr-> fr.getIdU1() == id);
        friendships.removeIf(fr-> fr.getIdU2() == id);
    }
}
