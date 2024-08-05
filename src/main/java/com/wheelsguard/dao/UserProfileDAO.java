package com.wheelsguard.dao;

import com.wheelsguard.model.UserProfile;
import com.wheelsguard.util.DatabaseUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserProfileDAO {
    private Connection connection;

    public UserProfileDAO(boolean isMySQL) throws SQLException {
        if (isMySQL) {
            this.connection = DatabaseUtil.getMySQLConnection();
        } else {
            this.connection = DatabaseUtil.getSQLServerConnection();
        }
    }

    public void insert(UserProfile userProfile) throws SQLException {
        String query = "INSERT INTO UserProfiles (UserID, Address, PhoneNumber, DateOfBirth, ProfilePicture) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, userProfile.getUserID());
            stmt.setString(2, userProfile.getAddress());
            stmt.setString(3, userProfile.getPhoneNumber());
            stmt.setDate(4, userProfile.getDateOfBirth());
            stmt.setBytes(5, userProfile.getProfilePicture());
            stmt.executeUpdate();
        }
    }

    public UserProfile get(int userProfileID) throws SQLException {
        String query = "SELECT * FROM UserProfiles WHERE UserProfileID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, userProfileID);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    UserProfile userProfile = new UserProfile();
                    userProfile.setUserProfileID(rs.getInt("UserProfileID"));
                    userProfile.setUserID(rs.getInt("UserID"));
                    userProfile.setAddress(rs.getString("Address"));
                    userProfile.setPhoneNumber(rs.getString("PhoneNumber"));
                    userProfile.setDateOfBirth(rs.getDate("DateOfBirth"));
                    userProfile.setProfilePicture(rs.getBytes("ProfilePicture"));
                    return userProfile;
                }
            }
        }
        return null;
    }

    public List<UserProfile> getAll() throws SQLException {
        List<UserProfile> userProfiles = new ArrayList<>();
        String query = "SELECT * FROM UserProfiles";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                UserProfile userProfile = new UserProfile();
                userProfile.setUserProfileID(rs.getInt("UserProfileID"));
                userProfile.setUserID(rs.getInt("UserID"));
                userProfile.setAddress(rs.getString("Address"));
                userProfile.setPhoneNumber(rs.getString("PhoneNumber"));
                userProfile.setDateOfBirth(rs.getDate("DateOfBirth"));
                userProfile.setProfilePicture(rs.getBytes("ProfilePicture"));
                userProfiles.add(userProfile);
            }
        }
        return userProfiles;
    }

    public void update(UserProfile userProfile) throws SQLException {
        String query = "UPDATE UserProfiles SET Address = ?, PhoneNumber = ?, DateOfBirth = ?, ProfilePicture = ? WHERE UserProfileID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, userProfile.getAddress());
            stmt.setString(2, userProfile.getPhoneNumber());
            stmt.setDate(3, userProfile.getDateOfBirth());
            stmt.setBytes(4, userProfile.getProfilePicture());
            stmt.setInt(5, userProfile.getUserProfileID());
            stmt.executeUpdate();
        }
    }

    public void delete(int userProfileID) throws SQLException {
        String query = "DELETE FROM UserProfiles WHERE UserProfileID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, userProfileID);
            stmt.executeUpdate();
        }
    }
}

