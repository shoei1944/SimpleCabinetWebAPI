package pro.gravit.simplecabinet.web.model.shop;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "service_products")
public class ServiceProduct extends Product {
    private ServiceType type;
    private boolean stackable;
    private int days;

    public ServiceType getType() {
        return type;
    }

    public void setType(ServiceType type) {
        this.type = type;
    }

    public boolean isStackable() {
        return stackable;
    }

    public void setStackable(boolean stackable) {
        this.stackable = stackable;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public enum ServiceType {
        CHANGE_PREFIX
    }
}
