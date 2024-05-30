package pro.gravit.simplecabinet.web.service.shop.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pro.gravit.simplecabinet.web.model.shop.ItemProduct;
import pro.gravit.simplecabinet.web.repository.shop.ItemSearchRepository;

@Service
public class ItemSearchService {
    @Autowired
    private ItemSearchRepository repository;

    public Page<ItemProduct> findByDisplayName (String displayName, Pageable pageable) {
        return repository.findByDisplayName(displayName, pageable);
    }
}
