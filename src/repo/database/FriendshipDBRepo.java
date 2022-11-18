package repo.database;

import domain.Friendship;
import repo.Repository;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class FriendshipDBRepo implements Repository<Long, Friendship> {
    private final JDBCUtils jdbcUtils = new JDBCUtils();

    @Override
    public List<Friendship> getAll() {
        List<Friendship> friendList = new ArrayList<>();

        String query = "SELECT * FROM friendships";

        try (Connection connection = jdbcUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()
        ) {
            while (resultSet.next()) {
                int u1ID = resultSet.getInt("idu1");
                int u2ID = resultSet.getInt("idu2");
                LocalDateTime date = resultSet.getTimestamp("date").toLocalDateTime();
                Friendship friendship = new Friendship(u1ID, u2ID, date);
                friendList.add(friendship);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return friendList;
    }

    @Override
    public boolean delete(Friendship entity) {
        String query = "DELETE FROM friendships WHERE idu1 = ? and idu2 = ?";
        try (Connection connection = jdbcUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)
        ) {
            statement.setInt(1, (int) entity.getIdU1());
            statement.setInt(2, (int) entity.getIdU2());
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    @Override
    public Friendship update(Friendship entity) {
        String query = "UPDATE friendships SET idu2 = ?, date = ? WHERE idu1= ?";
        try (Connection connection = jdbcUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
        ) {
            statement.setInt(1, (int) entity.getIdU2());
            statement.setTimestamp(2, Timestamp.valueOf(entity.getDate()));
            statement.setInt(3, (int) entity.getIdU1());
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return entity;
    }

    @Override
    public Friendship save(Friendship entity) {
        String query = "INSERT INTO friendships(idu1, idu2, date) VALUES(?,?,?)";
        try (Connection connection = jdbcUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)
        ) {
            statement.setInt(1, (int) entity.getIdU1());
            statement.setInt(2, (int) entity.getIdU2());
            statement.setTimestamp(3, Timestamp.valueOf(entity.getDate()));
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
