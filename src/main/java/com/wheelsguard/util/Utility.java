package com.wheelsguard.util;

import java.util.regex.Pattern;
import org.apache.commons.text.StringEscapeUtils;

public class Utility {
    public static boolean IS_MY_SQL = true;

    /**
     * Sanitizes input to prevent XSS attacks.
     *
     * @param input The input string to sanitize
     * @return The sanitized input string
     */
    public static String sanitizeInput(String input) {
        if (input == null) {
            return null;
        }
        // Use Apache Commons Text to escape HTML
        return StringEscapeUtils.escapeHtml4(input.trim());
    }

    /**
     * Validates an email address.
     *
     * @param email The email address to validate
     * @return true if the email is valid, false otherwise
     */
    public static boolean isValidEmail(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }
        // Simple regex pattern for email validation
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
    }
}