package pro.gravit.simplecabinet.web.repository;

import org.springframework.data.repository.CrudRepository;
import pro.gravit.simplecabinet.web.model.OrderEntity;

public interface OrderRepository extends CrudRepository<OrderEntity, Long> {
}
