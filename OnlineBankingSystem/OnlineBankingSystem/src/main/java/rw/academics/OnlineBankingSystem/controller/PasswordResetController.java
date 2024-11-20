package rw.academics.OnlineBankingSystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rw.academics.OnlineBankingSystem.service.PasswordResetService;

@Controller
public class PasswordResetController {

    private static final Logger logger = LoggerFactory.getLogger(PasswordResetController.class);

    @Autowired
    private PasswordResetService passwordResetService;

    // Display the Forgot Password form
    @GetMapping("/forgot-password")
    public String showForgotPasswordPage() {
        return "forgot-password"; // forgot-password.html
    }

    // Handle Forgot Password form submission
    @PostMapping("/forgot-password")
    public String handleForgotPassword(@RequestParam("email") String email, Model model) {
        try {
            passwordResetService.generateAndSendResetToken(email);
            model.addAttribute("message", "Password reset link sent to your email.");
            logger.info("Password reset link sent to {}", email);
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", "Email address not found.");
            logger.warn("Attempted password reset for non-existent email: {}", email);
        } catch (Exception e) {
            model.addAttribute("error", "An error occurred while sending the email. Please try again later.");
            logger.error("Error sending password reset email to {}: {}", email, e.getMessage());
        }
        return "forgot-password";
    }

    // Display the Reset Password form
    @GetMapping("/reset-password")
    public String showResetPasswordPage(@RequestParam("token") String token, Model model) {
        boolean isValidToken = passwordResetService.validateToken(token);
        if (!isValidToken) {
            model.addAttribute("error", "Invalid or expired token.");
            logger.warn("Invalid token access attempt: {}", token);
            return "reset-password"; // reset-password.html with error message
        }
        model.addAttribute("token", token);
        return "reset-password"; // reset-password.html
    }

    // Handle Reset Password form submission
    @PostMapping("/reset-password")
    public String handleResetPassword(
            @RequestParam("token") String token,
            @RequestParam("newPassword") String newPassword,
            Model model) {

        if (passwordResetService.validateToken(token)) {
            passwordResetService.updatePassword(token, newPassword);
            model.addAttribute("message", "Password successfully reset. You can now log in.");
            logger.info("Password reset successful for token: {}", token);
            return "login"; // Redirect to login page after successful password reset
        } else {
            model.addAttribute("error", "Invalid or expired token.");
            logger.warn("Attempt to reset password with invalid token: {}", token);
            return "reset-password";
        }
    }
}
