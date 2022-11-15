package repo.memory;

import domain.Friendship;
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
        friendships.add(entity);
        return entity;
    }

    @Override
    public Friendship update(Friendship entity) {
        if(entity == null)
            throw new IllegalArgumentException("entity must be not null!");
        validator.validate(entity);

        friendships.add(entity);

        if(friendships.get(Math.toIntExact(entity.getId())) != null) {
            friendships.add(entity);
            return null;
        }
        return entity;
    }
}
