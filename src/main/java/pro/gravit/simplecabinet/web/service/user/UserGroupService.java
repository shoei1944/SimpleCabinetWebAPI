package pro.gravit.simplecabinet.web.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pro.gravit.simplecabinet.web.model.user.User;
import pro.gravit.simplecabinet.web.model.user.UserGroup;
import pro.gravit.simplecabinet.web.repository.user.UserGroupRepository;

import java.time.LocalDateTime;
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

    public Optional<UserGroup> findByGroupNameAndUser(String groupName, User user) {
        return repository.findByGroupNameAndUser(groupName, user, LocalDateTime.now());
    }

    public void delete(UserGroup userGroup) {
        repository.delete(userGroup);
    }
}
