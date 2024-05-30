package pro.gravit.simplecabinet.web.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pro.gravit.simplecabinet.web.model.user.User;
import pro.gravit.simplecabinet.web.model.user.UserGroup;

import java.time.LocalDateTime;
import java.util.Optional;

public interface UserGroupRepository extends JpaRepository<UserGroup, Long> {
    @Query("select g from UserGroup g where g.groupName = ?1 and g.user = ?2 and (g.endDate is null or g.endDate > ?3)")
    Optional<UserGroup> findByGroupNameAndUser(String groupName, User user, LocalDateTime now);
}
