package repo.memory;

import domain.User;
import validators.Validator;

import java.util.ArrayList;
import java.util.List;

public class UserMemoryRepository extends AbstractMemoryRepo<User> {
    private List<User> users;

    public UserMemoryRepository(Validator<User> validator) {
        super(validator);
        this.users = new ArrayList<>();
    }


    @Override
    public void add(User entity) {
        validator.validate(entity);
        for (User u : users) {
            if (u.getID() == entity.getID()) {
                throw new IllegalArgumentException("The specified user is already logged-in!\n");
            }
        }
        users.add(entity);
    }

    @Override
    public void delete(int id) {
        for (User u : users) {
            for (User fr : u.getFriends()) {
                if (fr.getID() == id) {
                    u.getFriends().remove(fr);
                    break;
                }
            }
        }
        users.removeIf(u -> u.getID() == id);
    }

    @Override
    public List<User> getAll() {
        return this.users;
    }

    @Override
    public User save(User entity) {
        if (entity == null)
            throw new IllegalArgumentException("entity must be not null");
        validator.validate(entity);
        for (User u : users) {
            if (u.getID() == entity.getID()) {
                return entity;
            }
        }
        users.add(entity);
        return null;
    }

    @Override
    public User update(User entity) {
        if (entity == null)
            throw new IllegalArgumentException("entity must be not null!");
        validator.validate(entity);

        users.add(entity);

        if (users.get(Math.toIntExact(entity.getId())) != null) {
            users.add(entity);
            return null;
        }
        return entity;
    }
}
