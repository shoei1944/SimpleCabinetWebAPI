package pro.gravit.simplecabinet.web.repository.shop;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.gravit.simplecabinet.web.model.shop.ItemOrder;

public interface ItemOrderRepository extends JpaRepository<ItemOrder, Long> {
}
