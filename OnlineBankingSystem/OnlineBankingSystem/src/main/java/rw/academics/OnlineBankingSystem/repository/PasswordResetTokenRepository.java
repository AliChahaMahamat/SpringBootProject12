package rw.academics.OnlineBankingSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rw.academics.OnlineBankingSystem.model.PasswordResetToken;
import rw.academics.OnlineBankingSystem.model.MyAppUser;

import java.util.Optional;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
    Optional<PasswordResetToken> findByToken(String token);
    void deleteByUser(MyAppUser user);
}
