package repo.memory;

import domain.Entity;
import repo.Repository;
import validators.Validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class MemoryRepo<ID, T extends Entity<ID>> implements Repository<ID, T> {
    protected Validator<T> validator;
    protected List<T> entities;

    public MemoryRepo(Validator<T> validator) {
        this.validator = validator;
        this.entities = new ArrayList<>();
    }

    @Override
    public List<T> getAll(){
        return this.entities;
    }
    @Override
    public boolean delete(T entity){
        validator.validate(entity);
        return entities.remove(entity);
    }
    @Override
    public T update(T entity){
        if (entity == null)
            throw new IllegalArgumentException("entity must be not null!");
        validator.validate(entity);

        entities.add(entity);

        if (entities.get((Integer) entity.getId()) != null) {
            entities.add(entity);
            return null;
        }
        return entity;
    }
    @Override
    public T save(T entity){
        if (entity == null)
            throw new IllegalArgumentException("entity must be not null");
        validator.validate(entity);
        for (T u : entities) {
            if (Objects.equals(u, entity)) {
                return entity;
            }
        }
        entities.add(entity);
        return null;
    }
}
