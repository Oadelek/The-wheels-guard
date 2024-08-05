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

    public int insert(User user) throws SQLException {
        String query = "INSERT INTO Users (Username, PasswordHash, Email, FirstName, LastName, DateCreated, LastLoginDate, IsActive) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            // Set parameters
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPasswordHash());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getFirstName());
            stmt.setString(5, user.getLastName());
            stmt.setTimestamp(6, user.getDateCreated());
            stmt.setTimestamp(7, user.getLastLoginDate());
            stmt.setBoolean(8, user.isActive());

            // Execute the insert
            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                // Retrieve the generated UserID
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        return generatedKeys.getInt(1);
                    } else {
                        throw new SQLException("Creating user failed, no ID obtained.");
                    }
                }
            } else {
                throw new SQLException("Creating user failed, no rows affected.");
            }
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

    public User getUserByUsername(String username) throws SQLException {
        String query = "SELECT * FROM Users WHERE Username = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, username);

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
        } catch (SQLException e) {
            System.err.println("Error in getUserByUsername: " + e.getMessage());
            throw e; // Re-throw the exception after printing
        }

        return null; // Return null if no user is found with the given username
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

    public void updateLastLoginDate(int userId, Timestamp lastLoginDate) throws SQLException {
        String query = "UPDATE Users SET LastLoginDate = ? WHERE UserID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setTimestamp(1, new Timestamp(lastLoginDate.getTime()));
            stmt.setInt(2, userId);
            stmt.executeUpdate();
        }
    }
}
