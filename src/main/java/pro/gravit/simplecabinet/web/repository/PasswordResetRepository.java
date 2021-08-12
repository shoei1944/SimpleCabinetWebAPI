package pro.gravit.simplecabinet.web.repository;

import org.springframework.data.repository.CrudRepository;
import pro.gravit.simplecabinet.web.model.PasswordResetEntity;

public interface PasswordResetRepository extends CrudRepository<PasswordResetEntity, Long> {
}
