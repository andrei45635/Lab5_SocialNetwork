package repo.memory;

import validators.Validator;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractMemoryRepo<T> {
    protected Validator<T> validator;
    public AbstractMemoryRepo(Validator<T> validator) {
        this.validator = validator;
    }
    public abstract void add(T entity);
    public abstract void delete(int id);
    public abstract List<T> getAll();
    public abstract T save(T entity);
}
