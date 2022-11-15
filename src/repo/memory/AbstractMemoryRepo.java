package repo.memory;

import domain.Entity;
import domain.User;
import repo.Repository;
import validators.UserValidator;
import validators.Validator;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractMemoryRepo<T extends Entity<Long>> implements Repository<Long, T> {
    protected Validator<T> validator;

    public AbstractMemoryRepo(Validator<T> validator) {
        this.validator = (Validator<T>) new UserValidator();
    }

    @Override
    public abstract List<T> getAll();
    @Override
    public abstract void delete(int ID);
    @Override
    public abstract void add(T var1);
    @Override
    public abstract T update(T entity);
    @Override
    public abstract T save(T entity);
//    public AbstractMemoryRepo(Validator<T> validator) {
//        this.validator = validator;
//    }
//    public abstract void add(T entity);
//    public abstract void delete(int id);
//    public abstract List<T> getAll();
//    public abstract T save(T entity);
//    public abstract void update(T entity);
}
