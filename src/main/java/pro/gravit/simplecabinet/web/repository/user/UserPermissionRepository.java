package pro.gravit.simplecabinet.web.repository.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pro.gravit.simplecabinet.web.model.user.UserPermission;

import java.util.List;

public interface UserPermissionRepository extends JpaRepository<UserPermission, Long> {
    List<UserPermission> findByGroupName(String groupName);

    @Query("select p from UserPermission p where p.groupName in (:groupNames)")
    List<UserPermission> findByGroupNames(List<String> groupNames);

    Page<UserPermission> findByGroupName(String groupName, Pageable pageable);
}
