package pro.gravit.simplecabinet.web.dto;

import pro.gravit.simplecabinet.web.model.Order;

public class OrderDto {
    public final long id;
    public final long quantity;
    public final Order.OrderStatus status;

    public OrderDto(Order entity) {
        this.id = entity.getId();
        this.quantity = entity.getQuantity();
        this.status = entity.getStatus();
    }
}
