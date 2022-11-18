import domain.Friendship;
import repo.database.UserDBRepo;
import repo.file.FriendshipFileRepo;
import repo.file.UserFileRepo;
import user_interface.UserInterface;
import domain.User;
import service.Service;
import validators.FriendshipValidator;
import validators.UserValidator;
import validators.Validator;

import java.io.IOException;


public class Main {
    public Main() {
    }

    public static void main(String[] args) throws IOException {
        Validator<User> validator = new UserValidator();
        Validator<Friendship> friendshipValidator = new FriendshipValidator();
        UserDBRepo repo = new UserDBRepo();
//        String fileUsersName = "src\\users.csv";
        String fileFriendsName = "src\\friends.csv";
//        UserFileRepo repo;
//        try {
//            repo = new UserFileRepo(fileUsersName, validator);
//        } catch (IOException e) {
//            throw new IOException(e);
//        }
        FriendshipFileRepo friendships;
        try {
            friendships = new FriendshipFileRepo(fileFriendsName, friendshipValidator);
        } catch (IOException e) {
            throw new IOException(e);
        }
        Service service = new Service(validator, repo, friendships);
        UserInterface ui = new UserInterface(service);
        ui.start();
    }
}
