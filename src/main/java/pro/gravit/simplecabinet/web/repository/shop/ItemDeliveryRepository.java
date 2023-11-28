package pro.gravit.simplecabinet.web.repository.shop;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import pro.gravit.simplecabinet.web.model.shop.ItemDelivery;
import pro.gravit.simplecabinet.web.model.user.User;

public interface ItemDeliveryRepository extends JpaRepository<ItemDelivery, Long> {
    Page<ItemDelivery> findAllByUserAndCompleted(User user, boolean completed, Pageable pageable);
}
