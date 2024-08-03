package com.wheelsguard.model;

public class RolePermission {
    private int rolePermissionID;
    private int roleID;
    private int permissionID;

    public int getRolePermissionID() {
        return rolePermissionID;
    }

    public void setRolePermissionID(int rolePermissionID) {
        this.rolePermissionID = rolePermissionID;
    }

    public int getRoleID() {
        return roleID;
    }

    public void setRoleID(int roleID) {
        this.roleID = roleID;
    }

    public int getPermissionID() {
        return permissionID;
    }

    public void setPermissionID(int permissionID) {
        this.permissionID = permissionID;
    }
}
