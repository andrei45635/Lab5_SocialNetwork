package repo.database;

import domain.User;
import repo.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDBRepo implements Repository<Long, User> {
    private final JDBCUtils jdbcUtils = new JDBCUtils();

    @Override
    public List<User> getAll() {
        List<User> userList = new ArrayList<>();

        String query = "SELECT * FROM users";

        try (Connection connection = jdbcUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()
        ) {
            while (resultSet.next()) {
                int ID = resultSet.getInt("id");
                String firstName = resultSet.getString("firstname");
                String lastName = resultSet.getString("lastname");
                String email = resultSet.getString("email");
                String paswd = resultSet.getString("passwd");
                int age = resultSet.getInt("age");

                User user = new User(ID, firstName, lastName, email, paswd, age);
                userList.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return userList;
    }

    @Override
    public boolean delete(User entity) {
        String query = "DELETE FROM users WHERE id = ?";
        try (Connection connection = jdbcUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)
        ) {
            statement.setInt(1, entity.getID());
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    @Override
    public User update(User entity) {
        String query = "UPDATE users SET firstName = ?, lastName = ?, email = ?, passwd = ?, age = ? WHERE id = ?";
        try (Connection connection = jdbcUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
        ) {
            statement.setString(1, entity.getFirstName());
            statement.setString(2, entity.getLastName());
            statement.setString(3, entity.getEmail());
            statement.setString(4, entity.getPasswd());
            statement.setInt(5, entity.getAge());
            statement.setInt(6, entity.getID());
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return entity;
    }

    @Override
    public User save(User entity) {
        String query = "INSERT INTO users(ID, firstName, lastName, email, passwd, age) VALUES(?,?,?,?,?,?)";
        try (Connection connection = jdbcUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)
        ) {
            statement.setInt(1, entity.getID());
            statement.setString(2, entity.getFirstName());
            statement.setString(3, entity.getLastName());
            statement.setString(4, entity.getEmail());
            statement.setString(5, entity.getPasswd());
            statement.setInt(6, entity.getAge());
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return entity;
    }

    @Override
    public int size() {
        return getAll().size();
    }
}
