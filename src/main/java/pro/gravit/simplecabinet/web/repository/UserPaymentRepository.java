package pro.gravit.simplecabinet.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.gravit.simplecabinet.web.model.UserPayment;

import java.util.Optional;

public interface UserPaymentRepository extends JpaRepository<UserPayment, Long> {
    Optional<UserPayment> findUserPaymentBySystemAndSystemPaymentId(String system, String systemPaymentId);
}
