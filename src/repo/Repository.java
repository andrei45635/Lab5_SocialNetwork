
package repo;

import domain.User;
import java.util.List;

public interface Repository {
    List<User> getAllUsers();

    void deleteUser(int var1);

    void addUser(User var1);

    void addFriend(User var1, User var2);

    void deleteFriend(User var1, User var2);
}
