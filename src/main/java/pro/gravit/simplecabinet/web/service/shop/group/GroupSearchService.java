package pro.gravit.simplecabinet.web.service.shop.group;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pro.gravit.simplecabinet.web.model.shop.GroupProduct;
import pro.gravit.simplecabinet.web.repository.shop.GroupSearchRepository;

@Service
public class GroupSearchService {

    @Autowired
    private GroupSearchRepository repository;

    public Page<GroupProduct> findByDisplayName(String localName,Pageable pageable) {
        return repository.findByLocalName(localName, pageable);
    }
}
