package pro.gravit.simplecabinet.web.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import pro.gravit.simplecabinet.web.model.ProductEntity;

import java.util.List;

public interface ProductRepository extends CrudRepository<ProductEntity, Long> {
    List<ProductEntity> findAll(Pageable pageable);
}
