package pro.gravit.simplecabinet.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.gravit.simplecabinet.web.model.UserGroup;

public interface UserGroupRepository extends JpaRepository<UserGroup, Long> {
}
