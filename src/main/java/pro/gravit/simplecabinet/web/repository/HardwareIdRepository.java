package pro.gravit.simplecabinet.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.gravit.simplecabinet.web.model.HardwareId;

import java.util.Optional;

public interface HardwareIdRepository extends JpaRepository<HardwareId, Long> {
    Optional<HardwareId> findByPublicKey(byte[] publicKey);

    Optional<HardwareId> findByHwDiskId(String hwDiskId);
}
