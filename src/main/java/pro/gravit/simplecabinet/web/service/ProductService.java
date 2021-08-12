package pro.gravit.simplecabinet.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pro.gravit.simplecabinet.web.model.ProductEntity;
import pro.gravit.simplecabinet.web.repository.ProductRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository repository;

    public <S extends ProductEntity> S save(S entity) {
        return repository.save(entity);
    }

    public Optional<ProductEntity> findById(Long aLong) {
        return repository.findById(aLong);
    }

    public Iterable<ProductEntity> findAll() {
        return repository.findAll();
    }

    public void delete(ProductEntity entity) {
        repository.delete(entity);
    }

    public List<ProductEntity> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }
}
