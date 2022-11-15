package repo.memory;

import domain.Friendship;
import validators.Validator;

public class FriendshipMemoryRepo extends MemoryRepo<Long, Friendship> {
    public FriendshipMemoryRepo(Validator<Friendship> validator) {
        super(validator);
    }
}
