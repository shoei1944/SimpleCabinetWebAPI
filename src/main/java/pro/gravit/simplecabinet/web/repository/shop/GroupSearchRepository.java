package pro.gravit.simplecabinet.web.repository.shop;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pro.gravit.simplecabinet.web.model.shop.GroupProduct;

public interface GroupSearchRepository extends JpaRepository<GroupProduct, Long> {

    @Query("SELECT gp FROM GroupProduct gp WHERE gp.displayName ilike %:displayName%")
    Page<GroupProduct> findByLocalName(@Param("displayName") String localName,Pageable pageable);
}
