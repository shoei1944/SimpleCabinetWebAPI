package pro.gravit.simplecabinet.web.repository.shop;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import pro.gravit.simplecabinet.web.model.shop.ItemProduct;

public interface ItemProductRepository extends JpaRepository<ItemProduct, Long> {

    Page<ItemProduct> findAllByAvailable(Pageable pageable, boolean available);
}
