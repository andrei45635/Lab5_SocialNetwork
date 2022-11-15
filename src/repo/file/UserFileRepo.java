package repo.file;

import domain.User;
import validators.Validator;

import java.util.List;

public class UserFileRepo extends AbstractFileRepo<Long, User> {
    public UserFileRepo(String fileName, Validator<User> validator) {
        super(fileName, validator);
    }

    @Override
    public User save(User entity) {
        User e = super.save(entity);
        if (e == null) {
            writeToFile(entity);
        }
        return e;
    }

    @Override
    public void delete(User user) {
        super.delete(user);
        for (User u : getAll()) {
            System.out.println(u);
            writeToFile(user);
        }
    }

    @Override
    public User update(User user){
        if(super.update(user) != null){
            writeToFile(user);
        } else return null;
        return user;
    }

    @Override
    public User extractEntity(List<String> attrs) {
        return new User(Integer.parseInt(attrs.get(0)), attrs.get(1), attrs.get(2), attrs.get(3), attrs.get(4), Integer.parseInt(attrs.get(5)));
    }

    @Override
    protected String createEntityAsString(User entity) {
        return entity.getID() + ";" + entity.getFirstName() + ";" + entity.getLastName() + ";" + entity.getEmail() + ";" + entity.getPasswd() + ";" + entity.getAge();
    }
}
