package pro.gravit.simplecabinet.web.repository.shop;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import pro.gravit.simplecabinet.web.model.shop.Payment;
import pro.gravit.simplecabinet.web.model.user.User;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Page<Payment> findAllByUser(User user, Pageable pageable);

    Optional<Payment> findUserPaymentBySystemAndSystemPaymentId(String system, String systemPaymentId);
}
