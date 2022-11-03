import user_interface.UserInterface;
import domain.User;
import repo.memory.RepoMemory;
import service.Service;
import validators.UserValidator;
import validators.Validator;

public class Main {
    public Main() {
    }

    public static void main(String[] args) {
        Validator<User> validator = new UserValidator();
        RepoMemory repo = new RepoMemory(validator);
        Service service = new Service(validator, repo);
        UserInterface ui = new UserInterface(service);
        ui.start();
    }
}
