package pro.gravit.simplecabinet.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pro.gravit.simplecabinet.web.model.User;
import pro.gravit.simplecabinet.web.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private UserRepository repository;

    public <S extends User> S save(S entity) {
        return repository.save(entity);
    }

    public Optional<User> findById(Long aLong) {
        return repository.findById(aLong);
    }

    public Optional<User> findByUsername(String username) {
        return repository.findByUsername(username);
    }

    public Optional<User> findByUUID(UUID uuid) {
        return repository.findByUuid(uuid);
    }

    public List<User> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public User register(String username, String email, String password) {
        User user = new User();
        user.setUsername(username);
        user.setUuid(UUID.randomUUID());
        user.setEmail(email);
        user.setRawPassword(password);
        repository.save(user);
        return user;
    }
}
