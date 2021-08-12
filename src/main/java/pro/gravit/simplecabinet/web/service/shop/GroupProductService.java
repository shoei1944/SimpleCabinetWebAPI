package pro.gravit.simplecabinet.web.service.shop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pro.gravit.simplecabinet.web.exception.BalanceException;
import pro.gravit.simplecabinet.web.model.GroupOrder;
import pro.gravit.simplecabinet.web.model.GroupProduct;
import pro.gravit.simplecabinet.web.model.User;
import pro.gravit.simplecabinet.web.model.UserGroup;
import pro.gravit.simplecabinet.web.repository.GroupOrderRepository;
import pro.gravit.simplecabinet.web.repository.GroupProductRepository;
import pro.gravit.simplecabinet.web.service.UserGroupService;
import pro.gravit.simplecabinet.web.service.shop.delivery.GroupDeliveryService;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class GroupProductService {
    @Autowired
    private GroupProductRepository repository;
    @Autowired
    private GroupOrderRepository orderRepository;
    @Autowired
    private ShopService shopService;
    @Autowired
    private GroupDeliveryService deliveryService;
    @Autowired
    private UserGroupService userGroupService;

    public <S extends GroupProduct> S save(S entity) {
        return repository.save(entity);
    }

    public Page<GroupProduct> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Page<GroupProduct> findAllAvailable(Pageable pageable) {
        return repository.findByAvailable(pageable, true);
    }

    public Optional<GroupProduct> findById(Long aLong) {
        return repository.findById(aLong);
    }

    @Transactional
    public GroupOrder createGroupOrder(GroupProduct product, long quantity, User user) throws BalanceException {
        GroupOrder groupOrder = new GroupOrder();
        shopService.fillBasicOrderProperties(groupOrder, quantity, user);
        groupOrder.setProduct(product);
        orderRepository.save(groupOrder);
        shopService.makeTransaction(groupOrder, product);
        return groupOrder;
    }

    @Transactional
    public GroupOrder delivery(GroupOrder initialOrder) {
        var user = initialOrder.getUser();
        var groups = user.getGroups();
        var group = makeUserGroup(initialOrder);
        var product = initialOrder.getProduct();
        groups.add(group);
        userGroupService.save(group);
        shopService.fillProcessDeliveryOrderProperties(initialOrder);
        orderRepository.save(initialOrder);
        if (!product.isLocal()) {
            deliveryService.delivery(initialOrder);
        }
        return initialOrder;
    }

    private UserGroup makeUserGroup(GroupOrder order) {
        var product = order.getProduct();
        UserGroup userGroup = new UserGroup();
        userGroup.setGroupName(product.getLocalName());
        userGroup.setUser(order.getUser());
        userGroup.setStartDate(LocalDateTime.now());
        return userGroup;
    }
}
