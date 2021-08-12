package pro.gravit.simplecabinet.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pro.gravit.simplecabinet.web.model.BanInfoEntity;
import pro.gravit.simplecabinet.web.model.User;
import pro.gravit.simplecabinet.web.repository.BanInfoRepository;
import pro.gravit.simplecabinet.web.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class BanService {
    @Autowired
    private BanInfoRepository repository;
    @Autowired
    private UserRepository userRepository;

    public Page<BanInfoEntity> findAll(Pageable pageable) {
        return repository.findAllByShadow(false, pageable);
    }

    public <S extends BanInfoEntity> S save(S entity) {
        return repository.save(entity);
    }

    @Transactional
    public BanInfoEntity ban(User target, User moderator, String reason, long expireMinutes) {
        BanInfoEntity entity = new BanInfoEntity();
        entity.setTarget(target);
        entity.setModerator(moderator);
        entity.setReason(reason);
        var now = LocalDateTime.now();
        entity.setCreatedAt(now);
        if (expireMinutes > 0) {
            entity.setEndAt(now.plusMinutes(expireMinutes));
        }
        repository.save(entity);
        target.setBanInfo(entity);
        userRepository.save(target);
        return entity;
    }

    @Transactional
    public void unban(BanInfoEntity entity) {
        var user = entity.getTarget();
        user.setBanInfo(null);
    }

    public Optional<BanInfoEntity> findById(Long aLong) {
        return repository.findById(aLong);
    }

    public void delete(BanInfoEntity entity) {
        repository.delete(entity);
    }
}
