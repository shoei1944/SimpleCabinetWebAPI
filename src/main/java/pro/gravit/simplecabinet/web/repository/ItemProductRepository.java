package pro.gravit.simplecabinet.web.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import pro.gravit.simplecabinet.web.model.ItemProduct;

public interface ItemProductRepository extends JpaRepository<ItemProduct, Long> {

    Page<ItemProduct> findAllByAvailable(Pageable pageable, boolean available);
}
