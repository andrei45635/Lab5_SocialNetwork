package repo.file;

import domain.User;
import validators.Validator;

import java.util.List;

public class UserFileRepo extends AbstractFileRepo{
    public UserFileRepo(String fileName, Validator<User> validator) {
        super(fileName, validator);
    }

    @Override
    public User extractEntity(List<String> attrs) {
        return new User(Integer.parseInt(attrs.get(0)),attrs.get(1), attrs.get(2), attrs.get(3), attrs.get(4), Integer.parseInt(attrs.get(5)));
    }

    @Override
    protected String createEntityAsString(User entity) {
        return entity.getID()+";"+entity.getFirstName()+";"+entity.getLastName()+";"+entity.getEmail()+";"+entity.getPasswd()+";"+entity.getAge();
    }
}
