package pro.gravit.simplecabinet.web.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.gravit.simplecabinet.web.model.user.User;
import pro.gravit.simplecabinet.web.model.user.UserGroup;

import java.util.Optional;

public interface UserGroupRepository extends JpaRepository<UserGroup, Long> {
    Optional<UserGroup> findByGroupNameAndUser(String groupName, User user);
}
