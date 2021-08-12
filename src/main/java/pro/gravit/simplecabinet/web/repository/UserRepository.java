package pro.gravit.simplecabinet.web.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import pro.gravit.simplecabinet.web.model.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByUuid(UUID uuid);
    List<User> findAll(Pageable pageable);
}
