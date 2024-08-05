package com.wheelsguard.service;

import com.wheelsguard.dao.RoleDAO;
import com.wheelsguard.dao.UserDAO;
import com.wheelsguard.dao.UserRoleDAO;
import com.wheelsguard.model.User;
import com.wheelsguard.model.UserRole;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Timestamp;
import java.util.Date;
import java.sql.SQLException;
import java.util.List;

public class UserService {
    private UserDAO userDAO;
    private RoleDAO roleDAO;
    private UserRoleDAO userRoleDAO;

    public static final String SUPER_ADMIN_ROLE = "SUPER_ADMIN";
    public static final String MANAGER_ROLE = "MANAGER";
    public static final String SALES_REP_ROLE = "SALES_REP";
    public static final String SERVICE_TECHNICIAN_ROLE = "SERVICE_TECHNICIAN";
    public static final String INVENTORY_CLERK_ROLE = "INVENTORY_CLERK";

    public UserService(boolean isMySQL) throws SQLException {
        userDAO = new UserDAO(isMySQL);
        roleDAO = new RoleDAO(isMySQL);
        userRoleDAO = new UserRoleDAO(isMySQL);
    }

    public void addUser(User user) throws SQLException {
        int userID = userDAO.insert(user);
        int roleID = roleDAO.getRoleIDByRoleName(user.getUserRole());

        UserRole userRole = new UserRole();
        userRole.setUserID(userID);
        userRole.setRoleID(roleID);
        userRoleDAO.insert(userRole);
    }

    public User authenticateUser(String username, String password) throws SQLException {
        User user = userDAO.getUserByUsername(username);
        if (user != null && BCrypt.checkpw(password, user.getPasswordHash())) {
            List<String> permissions = userRoleDAO.getPermissionsByUserId(user.getUserID());
            List<String> roles = userRoleDAO.getRoleNamesByUserId(user.getUserID());
            user.setPermissions(permissions);
            user.setRoles(roles);

            // Update last login date
            user.setLastLoginDate(new Timestamp(System.currentTimeMillis()));
            userDAO.updateLastLoginDate(user.getUserID(), user.getLastLoginDate());

            return user;
        }
        return null;
    }

    public User getUser(int userID) throws SQLException {
        return userDAO.get(userID);
    }

    public List<User> getAllUsers() throws SQLException {
        try {
            return userDAO.getAll();
        } catch(SQLException e) {
            System.out.println("Error retrieving all users: "+ e.getMessage());

            // Rethrow the SQL exception
            throw e;
        }

    }

    public void updateUser(User user) throws SQLException {
        userDAO.update(user);
    }

    public void deleteUser(int userID) throws SQLException {
        userDAO.delete(userID);
    }
}
