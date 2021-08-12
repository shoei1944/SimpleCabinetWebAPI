package pro.gravit.simplecabinet.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.gravit.simplecabinet.web.model.AuditEntity;

public interface AuditRepository extends JpaRepository<AuditEntity, Long> {
}
