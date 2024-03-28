package pro.gravit.simplecabinet.web.model.shop;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "group_orders")
public class GroupOrder extends Order<GroupProduct> {
    private String server;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private GroupProduct product;

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public GroupProduct getProduct() {
        return product;
    }

    public void setProduct(GroupProduct product) {
        this.product = product;
    }
}
