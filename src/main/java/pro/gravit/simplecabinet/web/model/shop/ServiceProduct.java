package pro.gravit.simplecabinet.web.model.shop;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "service_products")
public class ServiceProduct extends Product {
    private ServiceType type;
    private boolean stackable;

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

    public enum ServiceType {

    }
}
