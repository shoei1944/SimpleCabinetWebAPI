package pro.gravit.simplecabinet.web.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import pro.gravit.simplecabinet.web.model.AuditEntity;

import java.util.List;

public interface AuditRepository extends CrudRepository<AuditEntity, Long> {
    List<AuditEntity> findAll(Pageable pageable);
}
