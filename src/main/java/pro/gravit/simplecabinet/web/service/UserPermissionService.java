package pro.gravit.simplecabinet.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pro.gravit.simplecabinet.web.model.UserPermission;
import pro.gravit.simplecabinet.web.repository.UserPermissionRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserPermissionService {
    @Autowired
    private UserPermissionRepository repository;

    public List<UserPermission> findByGroupName(String groupName) {
        return repository.findByGroupName(groupName);
    }

    public <S extends UserPermission> S save(S entity) {
        return repository.save(entity);
    }

    public Optional<UserPermission> findById(Long aLong) {
        return repository.findById(aLong);
    }
}
