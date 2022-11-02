package validators;

import domain.User;
import java.util.Objects;

public class UserValidator implements Validator<User> {
    public UserValidator() {
    }

    public void validate(User entity) throws ValidatorException {
        if (entity.getID() != entity.getID()) {
            throw new ValidatorException("ID has to be an integer\n");
        } else if (entity.getFirstName() != null && !Objects.equals(entity.getFirstName(), "")) {
            if (entity.getLastName() != null && !Objects.equals(entity.getLastName(), "")) {
                if (entity.getEmail() != null && !Objects.equals(entity.getEmail(), "")) {
                    if (entity.getPasswd() != null && !Objects.equals(entity.getPasswd(), "") && entity.getPasswd().length() >= 8) {
                        if (entity.getAge() < 13) {
                            throw new ValidatorException("You have to be over 13 to use this app.\n");
                        }
                    } else {
                        throw new ValidatorException("Password can't be null, empty or have less than 8 characters\n");
                    }
                } else {
                    throw new ValidatorException("Email can't be null or empty\n");
                }
            } else {
                throw new ValidatorException("Last Name can't be null or empty\n");
            }
        } else {
            throw new ValidatorException("First Name can't be null or empty\n");
        }
    }
}
