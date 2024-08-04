package com.wheelsguard.service;

import com.wheelsguard.dao.RoleDAO;
import com.wheelsguard.model.Role;

import java.sql.SQLException;
import java.util.List;

public class RoleService {
    private RoleDAO roleDAO;

    public RoleService(boolean isMySQL) throws SQLException {
        roleDAO = new RoleDAO(isMySQL);
    }

    public void addRole(Role role) throws SQLException {
        roleDAO.insert(role);
    }

    public Role getRole(int roleID) throws SQLException {
        return roleDAO.get(roleID);
    }

    public List<Role> getAllRoles() throws SQLException {
        return roleDAO.getAll();
    }

    public void updateRole(Role role) throws SQLException {
        roleDAO.update(role);
    }

    public void deleteRole(int roleID) throws SQLException {
        roleDAO.delete(roleID);
    }
}

