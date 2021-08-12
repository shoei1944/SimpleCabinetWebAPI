package pro.gravit.simplecabinet.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.gravit.simplecabinet.web.model.PasswordReset;

public interface PasswordResetRepository extends JpaRepository<PasswordReset, Long> {
}
