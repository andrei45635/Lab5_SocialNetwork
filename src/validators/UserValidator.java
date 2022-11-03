package validators;

import domain.User;
import java.util.Objects;

public class UserValidator implements Validator<User> {
    public UserValidator() {
    }

    public void validate(User entity) throws ValidatorException {
        String errors = "";
        if (entity.getID() != entity.getID()) {
           errors += "ID has to be an integer\n";
        }
        if (Objects.equals(entity.getFirstName(), "")) {
            errors += "First Name can't be null or empty\n";
        }
        if (Objects.equals(entity.getLastName(), "")) {
            errors += "Last Name can't be null or empty\n";
        }
        if (Objects.equals(entity.getEmail(), "")) {
           errors += "Email can't be null or empty\n";
        }
        if (Objects.equals(entity.getPasswd(), "") || entity.getPasswd().length() <= 8) {
           errors += "Password can't be null, empty or have less than 8 characters\n";
        }
        if (entity.getAge() < 13) {
            errors += "You have to be over 13 to use this app.\n";
        }
        if(errors.length() > 0){
            throw new ValidatorException(errors);
        }
    }
}
