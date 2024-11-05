package pro.gravit.simplecabinet.web.repository.shop;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import pro.gravit.simplecabinet.web.model.shop.ServiceProduct;

import java.util.Optional;

public interface ServiceProductRepository extends JpaRepository<ServiceProduct, Long> {
    Page<ServiceProduct> findAllByAvailable(Pageable pageable, boolean available);

    Optional<ServiceProduct> findByType(ServiceProduct.ServiceType type);
}
