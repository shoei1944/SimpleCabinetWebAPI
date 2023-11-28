package pro.gravit.simplecabinet.web.model.shop;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

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
