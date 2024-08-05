package com.wheelsguard.web.controller;

import com.wheelsguard.model.Role;
import com.wheelsguard.model.User;
import com.wheelsguard.service.RoleService;
import com.wheelsguard.service.UserService;
import com.wheelsguard.util.Utility;
import org.mindrot.jbcrypt.BCrypt;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/createUser")
public class CreateUserServlet extends HttpServlet {
    private UserService userService;
    private RoleService roleService;

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            this.userService = new UserService(Utility.IS_MY_SQL);
            this.roleService = new RoleService(Utility.IS_MY_SQL);
        } catch (SQLException e) {
            throw new ServletException("Error initializing the User Service and Role Service", e);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User currentUser = (User) request.getSession().getAttribute("user");
        if (currentUser != null && currentUser.hasPermission("MANAGE_USERS")) {
            try {
                List<Role> roles = roleService.getAllRoles();
                request.setAttribute("roles", roles);
                request.getRequestDispatcher("/WEB-INF/jsp/createUser.jsp").forward(request, response);
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
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            String email = request.getParameter("email");
            String firstName = request.getParameter("firstName");
            String lastName = request.getParameter("lastName");
            try {
                int roleId = Integer.parseInt(request.getParameter("roleId"));
                User newUser = new User();
                newUser.setUsername(username);
                newUser.setPasswordHash(BCrypt.hashpw(password, BCrypt.gensalt()));
                newUser.setEmail(email);
                newUser.setFirstName(firstName);
                newUser.setLastName(lastName);
                newUser.setRoleID(roleId);
                newUser.setActive(true);
                userService.addUser(newUser, roleId);
                response.sendRedirect(request.getContextPath() + "/userManagement");
            } catch (SQLException e) {
                throw new ServletException("Error creating user", e);
            }
        } else {
            response.sendRedirect(request.getContextPath() + "/login");
        }
    }
}
