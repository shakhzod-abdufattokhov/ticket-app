package uz.shaxzod.ticketapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.shaxzod.ticketapp.models.entity.PasswordResetToken;

import java.util.List;
import java.util.Optional;

@Repository
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, String> {
    @Modifying
    @Query("update PasswordResetToken t set t.used = true where t.user.id = :id")
    void markAllTokensAsUsed(String id);

    @Query("select t from PasswordResetToken t where t.tokenHash = :token and t.used = false")
    Optional<PasswordResetToken> findByTokenHash(String token);
}
