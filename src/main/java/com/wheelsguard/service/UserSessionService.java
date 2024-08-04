package com.wheelsguard.service;

import com.wheelsguard.dao.UserSessionDAO;
import com.wheelsguard.model.UserSession;

import java.sql.SQLException;
import java.util.List;

public class UserSessionService {
    private UserSessionDAO userSessionDAO;

    public UserSessionService(boolean isMySQL) throws SQLException {
        userSessionDAO = new UserSessionDAO(isMySQL);
    }

    public void addUserSession(UserSession userSession) throws SQLException {
        userSessionDAO.insert(userSession);
    }

    public UserSession getUserSession(int sessionID) throws SQLException {
        return userSessionDAO.get(sessionID);
    }

    public List<UserSession> getAllUserSessions() throws SQLException {
        return userSessionDAO.getAll();
    }

    public void updateUserSession(UserSession userSession) throws SQLException {
        userSessionDAO.update(userSession);
    }

    public void deleteUserSession(int sessionID) throws SQLException {
        userSessionDAO.delete(sessionID);
    }
}

