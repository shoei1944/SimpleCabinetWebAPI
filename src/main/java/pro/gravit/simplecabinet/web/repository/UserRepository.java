package pro.gravit.simplecabinet.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pro.gravit.simplecabinet.web.model.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("select u from User u where u.username = :usernameOrEmail or u.email = :usernameOrEmail")
    Optional<User> findByUsernameOrEmail(String usernameOrEmail);

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    Optional<User> findByUuid(UUID uuid);

    @Query("select u from User u left join fetch u.assets where u.username = :username")
    Optional<User> findByUsernameFetchAssets(String username);

    @Query("select u from User u left join fetch u.assets where u.uuid = :uuid")
    Optional<User> findByUuidFetchAssets(UUID uuid);

    @Query("select u from User u left join fetch u.assets where u.id = :id")
    Optional<User> findByIdFetchAssets(Long id);
}
