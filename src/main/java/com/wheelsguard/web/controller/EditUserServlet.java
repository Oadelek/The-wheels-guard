package com.wheelsguard.web.controller;

import com.wheelsguard.model.Role;
import com.wheelsguard.model.User;
import com.wheelsguard.service.RoleService;
import com.wheelsguard.service.UserService;
import com.wheelsguard.util.Utility;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/editUser")
public class EditUserServlet extends HttpServlet {
    private UserService userService;
    private RoleService roleService;

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            this.userService = new UserService(Utility.IS_MY_SQL);
            this.roleService = new RoleService(Utility.IS_MY_SQL);
        } catch (SQLException e) {
            throw new ServletException("Error initializing the User Service and Role service ", e);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User currentUser = (User) request.getSession().getAttribute("user");
        if (currentUser != null && currentUser.hasPermission("MANAGE_USERS")) {
            int userId = Integer.parseInt(request.getParameter("id"));
            User user;
            List<Role> roles;
            try {
                user = userService.getUser(userId);
                roles = roleService.getAllRoles();
                request.setAttribute("user", user);
                request.setAttribute("roles", roles);
                request.getRequestDispatcher("/WEB-INF/jsp/editUser.jsp").forward(request, response);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            response.sendRedirect(request.getContextPath() + "/login");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User currentUser = (User) request.getSession().getAttribute("user");
        if (currentUser != null && currentUser.hasPermission("MANAGE_USERS")) {
            int userId = Integer.parseInt(request.getParameter("id"));
            String username = request.getParameter("username");
            String email = request.getParameter("email");
            String firstName = request.getParameter("firstName");
            String lastName = request.getParameter("lastName");
            int roleId = Integer.parseInt(request.getParameter("roleId"));
            try {
                User user = userService.getUser(userId);
                user.setUsername(username);
                user.setEmail(email);
                user.setFirstName(firstName);
                user.setLastName(lastName);
                user.setRoleID(roleId);
                userService.updateUser(user);
                response.sendRedirect(request.getContextPath() + "/userManagement");
            } catch (SQLException e) {
                throw new ServletException("Error updating user", e);
            }
        } else {
            response.sendRedirect(request.getContextPath() + "/login");
        }
    }
}
