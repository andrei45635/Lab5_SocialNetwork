import domain.Friendship;
import repo.file.UserFileRepo;
import repo.memory.AbstractMemoryRepo;
import repo.memory.FriendshipMemoryRepo;
import repo.memory.UserMemoryRepository;
import user_interface.UserInterface;
import domain.User;
import service.Service;
import validators.FriendshipValidator;
import validators.UserValidator;
import validators.Validator;

import java.io.File;

public class Main {
    public Main() {
    }

    public static void main(String[] args) {
        System.out.println(new File("users.txt").getAbsolutePath());
        Validator<User> validator = new UserValidator();
        Validator<Friendship> friendshipValidator = new FriendshipValidator();
        //AbstractMemoryRepo<User> repo = new UserMemoryRepository(validator);
        UserFileRepo repo = new UserFileRepo("src\\users.txt", validator);
        FriendshipMemoryRepo friendships = new FriendshipMemoryRepo(friendshipValidator);
        Service service = new Service(validator, repo, friendships);
        UserInterface ui = new UserInterface(service);
        ui.start();
    }
}
