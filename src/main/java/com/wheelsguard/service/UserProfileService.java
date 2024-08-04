package com.wheelsguard.service;

import com.wheelsguard.dao.UserProfileDAO;
import com.wheelsguard.model.UserProfile;

import java.sql.SQLException;
import java.util.List;

public class UserProfileService {
    private UserProfileDAO userProfileDAO;

    public UserProfileService(boolean isMySQL) throws SQLException {
        userProfileDAO = new UserProfileDAO(isMySQL);
    }

    public void addUserProfile(UserProfile userProfile) throws SQLException {
        userProfileDAO.insert(userProfile);
    }

    public UserProfile getUserProfile(int userProfileID) throws SQLException {
        return userProfileDAO.get(userProfileID);
    }

    public List<UserProfile> getAllUserProfiles() throws SQLException {
        return userProfileDAO.getAll();
    }

    public void updateUserProfile(UserProfile userProfile) throws SQLException {
        userProfileDAO.update(userProfile);
    }

    public void deleteUserProfile(int userProfileID) throws SQLException {
        userProfileDAO.delete(userProfileID);
    }
}

