package pro.gravit.simplecabinet.web.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import pro.gravit.simplecabinet.web.model.GroupProduct;

public interface GroupProductRepository extends JpaRepository<GroupProduct, Long> {
    Page<GroupProduct> findByAvailable(Pageable pageable, boolean available);
}
