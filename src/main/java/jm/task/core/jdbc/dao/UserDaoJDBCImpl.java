package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }
    private final Util util = new Util();

    public void createUsersTable() {
        String sql = "CREATE TABLE userstable\n" +
        "(" +
                "Id SERIAL PRIMARY KEY,\n" +
                "name CHARACTER VARYING(30),\n" +
                "lastname CHARACTER VARYING(30),\n" +
                "age SMALLSERIAL,\n" +
                "email CHARACTER VARYING(30)\n" +
        " );";

        try (Connection connection = util.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void dropUsersTable() {
        String sql = "DROP TABLE IF EXISTS userstable";

        try (Connection connection = util.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String sql = "INSERT INTO userstable (name, lastName, age) VALUES (?, ?, ?)";

        try (Connection connection = util.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setString(1, name);
            ps.setString(2, lastName);
            ps.setByte(3, age);

            ps.executeUpdate();

            System.out.println("User с именем – " + name + " добавлен в базу данных");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeUserById(long id) {
        String sql = "DELETE FROM userstable WHERE id = ?";

        try (Connection connection = util.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setLong(1, id);

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();

        String sql = "SELECT id, name, lastName, age FROM userstable";

        try (Connection connection = util.getConnection()) {
            Statement stm = connection.createStatement();

            ResultSet rs = stm.executeQuery(sql);

            while(rs.next()) {

                userList.add(new User(rs.getLong("id"), rs.getString("name"),
                        rs.getString("lastName"), rs.getByte("age")));
            }
            return userList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void cleanUsersTable() {
        String sql = "DELETE FROM userstable";

        try (Connection connection = util.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
