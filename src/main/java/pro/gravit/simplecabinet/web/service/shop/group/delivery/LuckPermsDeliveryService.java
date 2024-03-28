package pro.gravit.simplecabinet.web.service.shop.group.delivery;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pro.gravit.simplecabinet.web.model.shop.GroupOrder;
import pro.gravit.simplecabinet.web.model.shop.GroupProduct;
import pro.gravit.simplecabinet.web.model.user.User;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Locale;

@Service("luckPermsDeliveryService")
public class LuckPermsDeliveryService implements GroupDeliveryService {
    @Value("${shop.group.luckperms.table}")
    private String table;
    @PersistenceContext
    private EntityManager manager;

    @Override
    @Transactional
    public void delivery(GroupOrder order) {
        User user = order.getUser();
        GroupProduct product = order.getProduct();
        Query query = manager.createNativeQuery(String.format("INSERT INTO %s (\"uuid\",\"permission\",value,\"server\",world,expiry,contexts) " +
                " VALUES (?,?,true,?,?,?,?);", table));
        query.setParameter(1, user.getUuid());
        query.setParameter(2, String.format("group.%s", product.getName().toLowerCase(Locale.ROOT)));
        query.setParameter(3, product.getServer());
        query.setParameter(4, product.getWorld());
        query.setParameter(5, product.getExpireDays() == 0 ? 0 : LocalDateTime.now().plusDays(product.getExpireDays() * order.getQuantity()).toEpochSecond(ZoneOffset.UTC));
        query.setParameter(6, product.getContext());
        query.executeUpdate();
    }
}
