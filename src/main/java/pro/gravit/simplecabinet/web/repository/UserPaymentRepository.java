package pro.gravit.simplecabinet.web.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import pro.gravit.simplecabinet.web.model.User;
import pro.gravit.simplecabinet.web.model.UserPayment;

import java.util.Optional;

public interface UserPaymentRepository extends JpaRepository<UserPayment, Long> {
    Page<UserPayment> findAllByUser(User user, Pageable pageable);

    Optional<UserPayment> findUserPaymentBySystemAndSystemPaymentId(String system, String systemPaymentId);
}
