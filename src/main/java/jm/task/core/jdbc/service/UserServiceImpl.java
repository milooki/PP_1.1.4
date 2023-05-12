package jm.task.core.jdbc.service;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;

import java.sql.SQLException;
import java.util.List;

public class UserServiceImpl implements UserService {
    private final UserDao uD = new UserDaoJDBCImpl();
    public void createUsersTable() throws  SQLException{
        uD.createUsersTable();
    }

    public void dropUsersTable() {
        uD.dropUsersTable();
    }

    public void saveUser(String name, String lastName, byte age) throws SQLException {
        uD.saveUser(name, lastName, age);
    }

    public void removeUserById(long id) throws SQLException {
        uD.removeUserById(id);
    }

    public List<User> getAllUsers() {
        return uD.getAllUsers();
    }

    public void cleanUsersTable() throws SQLException {
        uD.cleanUsersTable();
    }
}
