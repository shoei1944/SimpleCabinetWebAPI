package pro.gravit.simplecabinet.web.service.shop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pro.gravit.simplecabinet.web.exception.BalanceException;
import pro.gravit.simplecabinet.web.model.BalanceTransaction;
import pro.gravit.simplecabinet.web.model.shop.Order;
import pro.gravit.simplecabinet.web.model.shop.Product;
import pro.gravit.simplecabinet.web.model.user.User;
import pro.gravit.simplecabinet.web.service.user.BalanceService;

import java.time.LocalDateTime;

@Service
public class ShopService {
    @Autowired
    private BalanceService balanceService;

    public void fillBasicOrderProperties(Order order, long quantity, User user) {
        order.setUser(user);
        order.setQuantity(quantity);
        order.setStatus(Order.OrderStatus.INITIATED);
        order.setCreatedAt(LocalDateTime.now());
    }

    public void fillProcessDeliveryOrderProperties(Order order) {
        order.setStatus(Order.OrderStatus.DELIVERY);
        order.setUpdatedAt(LocalDateTime.now());
    }

    public BalanceTransaction makeTransaction(Order order, Product product) throws BalanceException {
        var sum = order.getQuantity() * product.getPrice();
        var balance = balanceService.findOrCreateUserBalanceByUserAndCurrency(order.getUser(), product.getCurrency());
        return balanceService.removeMoney(balance, sum, "Shop");
    }
}
