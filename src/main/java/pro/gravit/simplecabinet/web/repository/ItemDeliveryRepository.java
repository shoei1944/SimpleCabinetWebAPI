package pro.gravit.simplecabinet.web.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import pro.gravit.simplecabinet.web.model.ItemDelivery;
import pro.gravit.simplecabinet.web.model.User;

public interface ItemDeliveryRepository extends JpaRepository<ItemDelivery, Long> {
    Page<ItemDelivery> findAllByUserAndCompleted(User user, boolean completed, Pageable pageable);
}
