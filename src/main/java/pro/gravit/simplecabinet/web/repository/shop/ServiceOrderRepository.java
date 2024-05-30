package pro.gravit.simplecabinet.web.repository.shop;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pro.gravit.simplecabinet.web.model.shop.ServiceOrder;
import pro.gravit.simplecabinet.web.model.shop.ServiceProduct;
import pro.gravit.simplecabinet.web.model.user.User;

import java.util.Optional;

public interface ServiceOrderRepository extends JpaRepository<ServiceOrder, Long> {
    @Query("select o from ServiceOrder o join fetch o.product where o.user = ?1 and o.product.type = ?2 and o.status = 0")
    Optional<ServiceOrder> findByUserAndType(User user, ServiceProduct.ServiceType type);
}
