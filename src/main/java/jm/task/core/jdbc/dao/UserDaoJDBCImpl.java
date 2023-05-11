package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class UserDaoJDBCImpl implements UserDao {
    private final Connection connection = Util.getConnection();
    private static final String CREATE = "CREATE TABLE IF NOT EXISTS Users (id BIGINT AUTO_INCREMENT, " +
            "name VARCHAR(255) NOT NULL, " +
            "lastName VARCHAR(255) NOT NULL, " +
            "age TINYINT NOT NULL, " +
            "PRIMARY KEY (id));";
    private static final String DROP = "DROP TABLE IF EXISTS Users;";
    private static final String SAVE = "INSERT INTO users(name, lastname, age) VALUES (?, ?, ?)";
    private static final String DELETE = "DELETE FROM Users WHERE id = ?";
    private static final String SELECTALL = "SELECT * FROM Users;";
    private static final String CLEAR = "TRUNCATE TABLE Users;";


    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {

        try (Statement st = connection.createStatement()) {
            st.executeUpdate(CREATE);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Не удалось создать таблицу");
        }
    }

    public void dropUsersTable() {
        try (Statement st = connection.createStatement()) {
            st.execute(DROP);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Не удалось удалить таблицу");
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement ps = connection.prepareStatement(SAVE)) {
            ps.setString(1, name);
            ps.setString(2, lastName);
            ps.setByte(3, age);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Не удалось добавить юзера");
        }
    }

    public void removeUserById(long id) {
        try(PreparedStatement ps = connection.prepareStatement(DELETE)) {
            ps.setLong(1,id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Не удалось удалить User по id");
        }
    }

    public List<User> getAllUsers() {
        List<User> allUsers = new ArrayList<>();
        try(PreparedStatement ps = connection.prepareStatement(SELECTALL)) {
            ResultSet resultSet = ps.executeQuery(SELECTALL);
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastname"));
                user.setAge(resultSet.getByte("age"));
                allUsers.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("не удалось вывести всех User");
        }
        return allUsers;
    }

    public void cleanUsersTable() {
        try(PreparedStatement ps = connection.prepareStatement(CLEAR)) {
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Не удалось очистить таблицу");
        }
    }
}
