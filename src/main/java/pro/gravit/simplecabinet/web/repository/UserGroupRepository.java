package pro.gravit.simplecabinet.web.repository;

import org.springframework.data.repository.CrudRepository;
import pro.gravit.simplecabinet.web.model.UserGroup;

public interface UserGroupRepository extends CrudRepository<UserGroup, Long> {
}
