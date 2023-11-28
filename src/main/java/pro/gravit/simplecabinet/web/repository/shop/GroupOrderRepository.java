package pro.gravit.simplecabinet.web.repository.shop;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.gravit.simplecabinet.web.model.shop.GroupOrder;

public interface GroupOrderRepository extends JpaRepository<GroupOrder, Long> {
}
