package pro.gravit.simplecabinet.web.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import pro.gravit.simplecabinet.web.model.PaymentId;
import pro.gravit.simplecabinet.web.model.User;

import java.util.List;

public interface PaymentRepository extends CrudRepository<PaymentId, Long> {
    List<PaymentId> findAll(Pageable pageable);
    List<PaymentId> findByUser(User user, Pageable pageable);
}
