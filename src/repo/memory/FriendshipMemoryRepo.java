package repo.memory;

import domain.Friendship;
import validators.UserValidator;
import validators.Validator;

import java.util.ArrayList;
import java.util.List;

public class FriendshipMemoryRepo extends AbstractMemoryRepo<Friendship> {
    private List<Friendship> friendships;

    public FriendshipMemoryRepo(Validator<Friendship> validator) {
        super(validator);
        this.friendships = new ArrayList<>();
    }

    @Override
    public void add(Friendship entity) {
        friendships.add(entity);
    }

    @Override
    public void delete(int id) {
        friendships.removeIf(fr -> fr.getId() == id);
    }

    @Override
    public List<Friendship> getAll() {
        return this.friendships;
    }

    @Override
    public Friendship save(Friendship entity) {
        return null;
    }
}