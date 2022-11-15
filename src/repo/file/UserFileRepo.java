package repo.file;

import domain.User;
import validators.Validator;

import java.io.*;
import java.util.Arrays;
import java.util.List;

public class UserFileRepo extends AbstractFileRepo<Long, User> {
    public UserFileRepo(String fileName, Validator<User> validator) {
        super(fileName, validator);
        loadData();
    }

    @Override
    public User save(User entity) {
        User e = super.save(entity);
        if (e == null) {
            writeToFile();
        }
        return e;
    }

    @Override
    public boolean delete(User user) {
        boolean ret = super.delete(user);
        if(ret){
            writeToFile();
        }
        return false;
    }

    @Override
    public User update(User user){
        if(super.update(user) != null){
            writeToFile();
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
