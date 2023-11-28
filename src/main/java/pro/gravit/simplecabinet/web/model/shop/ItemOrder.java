package pro.gravit.simplecabinet.web.model.shop;

import javax.persistence.*;

@Entity
@Table(name = "item_orders")
public class ItemOrder extends Order<ItemProduct> {
    private long quantity;
    @Column(name = "custom_params")
    private String customParams;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private ItemProduct product;

    @Override
    public long getQuantity() {
        return quantity;
    }

    @Override
    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public String getCustomParams() {
        return customParams;
    }

    public void setCustomParams(String customParams) {
        this.customParams = customParams;
    }

    public ItemProduct getProduct() {
        return product;
    }

    public void setProduct(ItemProduct product) {
        this.product = product;
    }
}
