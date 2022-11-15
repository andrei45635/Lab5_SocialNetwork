package repo.memory;

import domain.User;
import validators.Validator;


public class UserMemoryRepository extends MemoryRepo<Long, User> {
    public UserMemoryRepository(Validator<User> validator) {
        super(validator);
    }
}
