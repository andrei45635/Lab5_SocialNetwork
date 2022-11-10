package repo.memory;

import domain.Entity;

public abstract class AbstractMemoryRepo<T> {
    public abstract void add(T entity);
    public abstract void delete(int id);
}
