package pro.gravit.simplecabinet.web.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "orders_generator")
    @SequenceGenerator(name = "orders_generator", sequenceName = "orders_seq", allocationSize = 1)
    private long id;
    @ManyToOne
    @JoinColumn(name = "payment_id")
    private UserPayment payment;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    private long quantity;
    private OrderStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public long getId() {
        return id;
    }

    public UserPayment getPayment() {
        return payment;
    }

    public void setPayment(UserPayment payment) {
        this.payment = payment;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public enum OrderStatus {
        INITIATED, DELIVERY
    }
}
