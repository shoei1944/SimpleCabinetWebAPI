package pro.gravit.simplecabinet.web.service.shop.delivery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pro.gravit.simplecabinet.web.model.ItemDelivery;
import pro.gravit.simplecabinet.web.model.ItemOrder;
import pro.gravit.simplecabinet.web.repository.ItemDeliveryRepository;

@Service
public class DatabaseItemDeliveryService implements ItemDeliveryService {
    @Autowired
    private ItemDeliveryRepository deliveryRepository;

    @Override
    public void delivery(ItemOrder order) {
        var delivery = makeItemDelivery(order);
        deliveryRepository.save(delivery);
    }

    private ItemDelivery makeItemDelivery(ItemOrder order) {
        var delivery = new ItemDelivery();
        var product = order.getProduct();
        delivery.setPart(order.getQuantity() * product.getItemQuantity());
        delivery.setCompleted(false);
        delivery.setItemName(product.getItemName());
        delivery.setItemExtra(product.getItemExtra());
        delivery.setItemEnchants(product.getItemEnchants());
        delivery.setItemNbt(product.getItemNbt());
        delivery.setUser(order.getUser());
        return delivery;
    }
}
