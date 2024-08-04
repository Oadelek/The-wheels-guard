package com.wheelsguard.service;

import com.wheelsguard.dao.PasswordResetTokenDAO;
import com.wheelsguard.model.PasswordResetToken;

import java.sql.SQLException;
import java.util.List;

public class PasswordResetTokenService {
    private PasswordResetTokenDAO passwordResetTokenDAO;

    public PasswordResetTokenService(boolean isMySQL) throws SQLException {
        passwordResetTokenDAO = new PasswordResetTokenDAO(isMySQL);
    }

    public void addPasswordResetToken(PasswordResetToken token) throws SQLException {
        passwordResetTokenDAO.insert(token);
    }

    public PasswordResetToken getPasswordResetToken(int tokenID) throws SQLException {
        return passwordResetTokenDAO.get(tokenID);
    }

    public List<PasswordResetToken> getAllPasswordResetTokens() throws SQLException {
        return passwordResetTokenDAO.getAll();
    }

    public void updatePasswordResetToken(PasswordResetToken token) throws SQLException {
        passwordResetTokenDAO.update(token);
    }

    public void deletePasswordResetToken(int tokenID) throws SQLException {
        passwordResetTokenDAO.delete(tokenID);
    }
}

