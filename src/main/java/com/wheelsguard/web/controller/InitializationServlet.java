package com.wheelsguard.web.controller;


import com.wheelsguard.model.User;
import com.wheelsguard.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/initialize")
public class InitializationServlet extends HttpServlet {
    private UserService userService = new UserService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (userService.getAllUsers().isEmpty()) {
            User superAdmin = new User();
            superAdmin.setUsername("superadmin");
            superAdmin.setEmail("superadmin@example.com");
            String tempPassword = generateTempPassword();

            // Hash the temporary password before storing it
            String hashedPassword = BCrypt.hashpw(tempPassword, BCrypt.gensalt());
            superAdmin.setPasswordHash(hashedPassword);

            superAdmin.setRole(Role.SUPER_ADMIN);

            userService.createUser(superAdmin);

            response.getWriter().println("Super Admin created. Username: superadmin, Temporary Password: " + tempPassword);
            response.getWriter().println("Please change this password upon first login.");
        } else {
            response.getWriter().println("System already initialized.");
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

