package com.wheelsguard.service;

import com.wheelsguard.dao.UserDAO;
import com.wheelsguard.model.User;

import java.sql.SQLException;
import java.util.List;

public class UserService {
    private UserDAO userDAO;

    public UserService(boolean isMySQL) throws SQLException {
        userDAO = new UserDAO(isMySQL);
    }

    public void addUser(User user) throws SQLException {
        userDAO.insert(user);
    }

    public User getUser(int userID) throws SQLException {
        return userDAO.get(userID);
    }

    public List<User> getAllUsers() throws SQLException {
        return userDAO.getAll();
    }

    public void updateUser(User user) throws SQLException {
        userDAO.update(user);
    }

    public void deleteUser(int userID) throws SQLException {
        userDAO.delete(userID);
    }
}
