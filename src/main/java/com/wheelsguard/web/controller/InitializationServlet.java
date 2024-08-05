package com.wheelsguard.web.controller;


import com.wheelsguard.model.User;
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
import java.util.Random;

@WebServlet("/initialize")
public class InitializationServlet extends HttpServlet {
    private UserService mysqlUserService;

    public InitializationServlet() throws SQLException {
        super();
        this.mysqlUserService = new UserService(Utility.IS_MY_SQL);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            if (mysqlUserService.getAllUsers().isEmpty()) {
                User superAdmin = new User();
                superAdmin.setUsername("superadmin");
                superAdmin.setEmail("superadmin@example.com");
                String tempPassword = generateTempPassword();

                // Hash the temporary password before storing it
                String hashedPassword = BCrypt.hashpw(tempPassword, BCrypt.gensalt());
                superAdmin.setPasswordHash(hashedPassword);

                superAdmin.setUserRole(UserService.SUPER_ADMIN_ROLE);

                mysqlUserService.addUser(superAdmin, 1);

                response.getWriter().println("Super Admin created. Username: superadmin, Email: superadmin@example.com, Temporary Password: " + tempPassword);
                response.getWriter().println("Please change this password upon first login.");
            } else {
                response.getWriter().println("System already initialized.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    private String generateTempPassword() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()";
        StringBuilder tempPassword = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 12; i++) {
            tempPassword.append(characters.charAt(random.nextInt(characters.length())));
        }
        return tempPassword.toString();
    }
}

