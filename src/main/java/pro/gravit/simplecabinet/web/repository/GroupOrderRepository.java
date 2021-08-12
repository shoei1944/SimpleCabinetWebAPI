package pro.gravit.simplecabinet.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.gravit.simplecabinet.web.model.GroupOrder;

public interface GroupOrderRepository extends JpaRepository<GroupOrder, Long> {
}
