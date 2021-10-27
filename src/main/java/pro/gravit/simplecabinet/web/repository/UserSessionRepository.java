package pro.gravit.simplecabinet.web.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import pro.gravit.simplecabinet.web.model.User;
import pro.gravit.simplecabinet.web.model.UserSession;

import java.util.Optional;

public interface UserSessionRepository extends JpaRepository<UserSession, Long> {
    Page<UserSession> findByUser(User user, Pageable pageable);

    Page<UserSession> findByUserAndDeleted(User user, boolean deleted, Pageable pageable);

    Optional<UserSession> findByRefreshToken(String refreshToken);

    Optional<UserSession> findByUserAndServerId(User user, String serverId);
}
