package pro.gravit.simplecabinet.web.model.shop;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "service_orders")
public class ServiceOrder extends Order<ServiceProduct> {
    @ManyToOne
    @JoinColumn(name = "product_id")
    private ServiceProduct product;

    @Override
    public ServiceProduct getProduct() {
        return product;
    }

    public void setProduct(ServiceProduct product) {
        this.product = product;
    }
}
