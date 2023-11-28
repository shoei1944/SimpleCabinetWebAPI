package pro.gravit.simplecabinet.web.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.gravit.simplecabinet.web.model.user.PasswordReset;

public interface PasswordResetRepository extends JpaRepository<PasswordReset, Long> {
}
