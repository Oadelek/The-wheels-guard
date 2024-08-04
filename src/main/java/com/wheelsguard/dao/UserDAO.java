package com.wheelsguard.dao;

import com.wheelsguard.model.User;
import com.wheelsguard.util.DatabaseUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    private Connection connection;

    public UserDAO(boolean isMySQL) throws SQLException {
        if (isMySQL) {
            this.connection = DatabaseUtil.getMySQLConnection();
        } else {
            this.connection = DatabaseUtil.getSQLServerConnection();
        }
    }

    public void insert(User user) throws SQLException {
        String query = "INSERT INTO Users (UserID, Username, PasswordHash, Email, FirstName, LastName, DateCreated, LastLoginDate, IsActive) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, user.getUserID());
            stmt.setString(2, user.getUsername());
            stmt.setString(3, user.getPasswordHash());
            stmt.setString(4, user.getEmail());
            stmt.setString(5, user.getFirstName());
            stmt.setString(6, user.getLastName());
            stmt.setTimestamp(7, user.getDateCreated());
            stmt.setTimestamp(8, user.getLastLoginDate());
            stmt.setBoolean(9, user.isActive());
            stmt.executeUpdate();
        }
    }

    public User get(int userID) throws SQLException {
        String query = "SELECT * FROM Users WHERE UserID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, userID);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    User user = new User();
                    user.setUserID(rs.getInt("UserID"));
                    user.setUsername(rs.getString("Username"));
                    user.setPasswordHash(rs.getString("PasswordHash"));
                    user.setEmail(rs.getString("Email"));
                    user.setFirstName(rs.getString("FirstName"));
                    user.setLastName(rs.getString("LastName"));
                    user.setDateCreated(rs.getTimestamp("DateCreated"));
                    user.setLastLoginDate(rs.getTimestamp("LastLoginDate"));
                    user.setActive(rs.getBoolean("IsActive"));
                    return user;
                }
            }
        }
        return null;
    }

    public List<User> getAll() throws SQLException {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM Users";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                User user = new User();
                user.setUserID(rs.getInt("UserID"));
                user.setUsername(rs.getString("Username"));
                user.setPasswordHash(rs.getString("PasswordHash"));
                user.setEmail(rs.getString("Email"));
                user.setFirstName(rs.getString("FirstName"));
                user.setLastName(rs.getString("LastName"));
                user.setDateCreated(rs.getTimestamp("DateCreated"));
                user.setLastLoginDate(rs.getTimestamp("LastLoginDate"));
                user.setActive(rs.getBoolean("IsActive"));
                users.add(user);
            }
        }
        return users;
    }

    public void update(User user) throws SQLException {
        String query = "UPDATE Users SET Username = ?, PasswordHash = ?, Email = ?, FirstName = ?, LastName = ?, DateCreated = ?, LastLoginDate = ?, IsActive = ? WHERE UserID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPasswordHash());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getFirstName());
            stmt.setString(5, user.getLastName());
            stmt.setTimestamp(6, user.getDateCreated());
            stmt.setTimestamp(7, user.getLastLoginDate());
            stmt.setBoolean(8, user.isActive());
            stmt.setInt(9, user.getUserID());
            stmt.executeUpdate();
        }
    }

    public void delete(int userID) throws SQLException {
        String query = "DELETE FROM Users WHERE UserID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, userID);
            stmt.executeUpdate();
        }
    }
}
