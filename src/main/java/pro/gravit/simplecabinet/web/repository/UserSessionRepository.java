package pro.gravit.simplecabinet.web.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import pro.gravit.simplecabinet.web.model.User;
import pro.gravit.simplecabinet.web.model.UserSession;

import java.util.Optional;

public interface UserSessionRepository extends JpaRepository<UserSession, Long> {
    Page<UserSession> findByUser(User user, Pageable pageable);

    Page<UserSession> findByUserAndDeleted(User user, boolean deleted, Pageable pageable);

    Optional<UserSession> findByRefreshToken(String refreshToken);

    Optional<UserSession> findByUserAndServerId(User user, String serverId);

    @Modifying
    @Query("UPDATE UserSession s set s.refreshToken = :newRefreshToken where s.refreshToken = :oldRefreshToken and s.deleted = false")
    int refreshSession(String newRefreshToken, String oldRefreshToken);

    @Modifying
    @Query("UPDATE UserSession s set s.deleted = true where s.id = :id")
    int deactivateById(long id);
}
