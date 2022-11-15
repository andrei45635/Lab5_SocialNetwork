package validators;

import domain.Friendship;
import domain.User;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FriendshipValidator implements Validator<Friendship> {
    public FriendshipValidator() {
    }

    @Override
    public void validate(Friendship entity) throws ValidatorException {
        String errors = "";
        if(entity.getIdU1() < 0 || entity.getIdU2() < 0){
            errors += "Invalid ID\n";
        }
        if(errors.length() > 0){
            throw new ValidatorException(errors);
        }
    }
}
