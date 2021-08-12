package pro.gravit.simplecabinet.web.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import pro.gravit.simplecabinet.web.model.BanInfoEntity;

public interface BanInfoRepository extends JpaRepository<BanInfoEntity, Long> {
    Page<BanInfoEntity> findAllByShadow(boolean shadow, Pageable pageable);
}
