package pro.gravit.simplecabinet.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.gravit.simplecabinet.web.model.ItemOrder;

public interface ItemOrderRepository extends JpaRepository<ItemOrder, Long> {
}
