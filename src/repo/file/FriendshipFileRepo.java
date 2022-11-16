package repo.file;

import domain.Friendship;
import validators.Validator;

import java.time.LocalDateTime;
import java.util.List;

public class FriendshipFileRepo extends AbstractFileRepo<Long, Friendship> {
    public FriendshipFileRepo(String fileName, Validator<Friendship> validator) {
        super(fileName, validator);
        loadData();
    }

    @Override
    public Friendship save(Friendship entity) {
        Friendship e = super.save(entity);
        if (e == null) {
            writeToFile();
        }
        return e;
    }

    @Override
    public boolean delete(Friendship user) {
        boolean ret = super.delete(user);
        if (ret) {
            writeToFile();
        }
        return false;
    }

    @Override
    public Friendship update(Friendship user) {
        if (super.update(user) != null) {
            writeToFile();
        } else return null;
        return user;
    }

    @Override
    public Friendship extractEntity(List<String> attrs) {
        return new Friendship(Integer.parseInt(attrs.get(0)), Integer.parseInt(attrs.get(1)), LocalDateTime.parse(attrs.get(2)));
    }

    @Override
    protected String createEntityAsString(Friendship entity) {
        return entity.getIdU1()+";"+entity.getIdU2()+";"+entity.getDate();
    }
}
