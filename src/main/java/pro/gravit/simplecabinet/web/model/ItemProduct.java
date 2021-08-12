package pro.gravit.simplecabinet.web.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "item_products")
public class ItemProduct extends Product {
    // Delivery item data
    @Column(name = "item_name")
    private String itemName;
    @Column(name = "item_quantity")
    private int itemQuantity;
    @Column(name = "item_extra")
    private String itemExtra;
    @Column(name = "item_enchants")
    private String itemEnchants;
    @Column(name = "item_nbt")
    private String itemNbt;
    @Column(name = "item_custom")
    private String itemCustom;
    // Delivery server
    private String server;

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(int itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    public String getItemExtra() {
        return itemExtra;
    }

    public void setItemExtra(String itemExtra) {
        this.itemExtra = itemExtra;
    }

    public String getItemEnchants() {
        return itemEnchants;
    }

    public void setItemEnchants(String itemEnchants) {
        this.itemEnchants = itemEnchants;
    }

    public String getItemNbt() {
        return itemNbt;
    }

    public void setItemNbt(String itemNbt) {
        this.itemNbt = itemNbt;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public String getItemCustom() {
        return itemCustom;
    }

    public void setItemCustom(String itemCustom) {
        this.itemCustom = itemCustom;
    }
}
