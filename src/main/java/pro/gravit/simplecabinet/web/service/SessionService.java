package pro.gravit.simplecabinet.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pro.gravit.simplecabinet.web.model.User;
import pro.gravit.simplecabinet.web.model.UserSession;
import pro.gravit.simplecabinet.web.repository.UserSessionRepository;
import pro.gravit.simplecabinet.web.utils.SecurityUtils;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class SessionService {
    @Autowired
    private UserSessionRepository repository;

    public UserSession create(User user, String client) {
        UserSession session = new UserSession();
        session.setUser(user);
        session.setClient(client);
        session.setRefreshToken(SecurityUtils.generateRandomString(32));
        session.setCreatedAt(LocalDateTime.now());
        repository.save(session);
        return session;
    }

    public <S extends UserSession> S save(S entity) {
        return repository.save(entity);
    }

    public UserSession update(UserSession session) {
        session.setRefreshToken(SecurityUtils.generateRandomString(32));
        repository.save(session);
        return session;
    }

    public Optional<UserSession> findByRefreshToken(String refreshToken) {
        return repository.findByRefreshToken(refreshToken);
    }

    public Optional<UserSession> findByUserAndServerId(User user, String serverId) {
        return repository.findByUserAndServerId(user, serverId);
    }

    public Page<UserSession> findByUser(User user, Pageable pageable) {
        return repository.findByUser(user, pageable);
    }

    public Page<UserSession> findByUserPublic(User user, Pageable pageable) {
        return repository.findByUserAndDeleted(user, false, pageable);
    }

    public Optional<UserSession> findById(Long aLong) {
        return repository.findById(aLong);
    }

    public void delete(UserSession entity) {
        repository.delete(entity);
    }
}
