package pro.gravit.simplecabinet.web.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pro.gravit.simplecabinet.web.model.BanInfoEntity;
import pro.gravit.simplecabinet.web.model.User;

import java.time.LocalDateTime;
import java.util.Optional;

public interface BanInfoRepository extends JpaRepository<BanInfoEntity, Long> {
    Page<BanInfoEntity> findAllByShadow(boolean shadow, Pageable pageable);

    @Query("select m from BanInfo m where m.target = ?1 and ( m.endAt is null or m.endAt > ?2 )")
    Optional<BanInfoEntity> findBanByUser(User target, LocalDateTime now);
}
