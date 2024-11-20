package rw.academics.OnlineBankingSystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import rw.academics.OnlineBankingSystem.model.MyAppUser;
import rw.academics.OnlineBankingSystem.model.MyAppUserRepository;
import rw.academics.OnlineBankingSystem.model.PasswordResetToken;

import rw.academics.OnlineBankingSystem.repository.PasswordResetTokenRepository;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class PasswordResetService {

    @Autowired
    private MyAppUserRepository userRepository;

    @Autowired
    private PasswordResetTokenRepository tokenRepository;

    @Autowired
    private EmailService emailService;

    public void generateAndSendResetToken(String email) {
        MyAppUser user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // Generate token
        String token = UUID.randomUUID().toString();

        // Save token to database
        PasswordResetToken resetToken = new PasswordResetToken();
        resetToken.setToken(token);
        resetToken.setUser(user);
        resetToken.setExpiryDate(new Date(System.currentTimeMillis() + 1000 * 60 * 60)); // 1 hour expiration

        tokenRepository.save(resetToken);

        // Send reset email
        String resetUrl = "http://localhost:8081/reset-password?token=" + token;
        emailService.sendSimpleEmail(user.getEmail(), "Password Reset Request",
                "To reset your password, click the link below:\n" + resetUrl);
    }

    public boolean validateToken(String token) {
        Optional<PasswordResetToken> resetToken = tokenRepository.findByToken(token);
        return resetToken.isPresent() && resetToken.get().getExpiryDate().after(new Date());
    }

    public void updatePassword(String token, String newPassword) {
        PasswordResetToken resetToken = tokenRepository.findByToken(token)
                .orElseThrow(() -> new IllegalArgumentException("Invalid token"));

        MyAppUser user = resetToken.getUser();
        user.setPassword(new BCryptPasswordEncoder().encode(newPassword)); // Encrypt password

        userRepository.save(user);

        // Remove token after password reset
        tokenRepository.delete(resetToken);
    }
}
