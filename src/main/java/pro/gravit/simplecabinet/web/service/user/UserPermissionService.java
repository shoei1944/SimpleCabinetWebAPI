package pro.gravit.simplecabinet.web.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pro.gravit.simplecabinet.web.model.user.UserPermission;
import pro.gravit.simplecabinet.web.repository.user.UserPermissionRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserPermissionService {
    @Autowired
    private UserPermissionRepository repository;

    public List<UserPermission> findByGroupName(String groupName) {
        return repository.findByGroupName(groupName);
    }

    public List<UserPermission> findByGroupNames(List<String> groupNames) {
        return repository.findByGroupNames(groupNames);
    }

    public Page<UserPermission> findByGroupName(String groupName, Pageable pageable) {
        return repository.findByGroupName(groupName, pageable);
    }

    public <S extends UserPermission> S save(S entity) {
        return repository.save(entity);
    }

    public Optional<UserPermission> findById(Long aLong) {
        return repository.findById(aLong);
    }
}
