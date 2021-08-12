package pro.gravit.simplecabinet.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pro.gravit.simplecabinet.web.model.UserGroup;
import pro.gravit.simplecabinet.web.repository.UserGroupRepository;

import java.util.Optional;

@Service
public class UserGroupService {
    @Autowired
    private UserGroupRepository repository;

    public <S extends UserGroup> S save(S entity) {
        return repository.save(entity);
    }

    public Optional<UserGroup> findById(Long aLong) {
        return repository.findById(aLong);
    }
}
