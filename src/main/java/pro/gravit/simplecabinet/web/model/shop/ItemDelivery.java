package pro.gravit.simplecabinet.web.model.shop;

import jakarta.persistence.*;
import pro.gravit.simplecabinet.web.model.user.User;

@Entity
@Table(name = "item_delivery")
public class ItemDelivery {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "item_delivery_generator")
    @SequenceGenerator(name = "item_delivery_generator", sequenceName = "item_delivery_seq", allocationSize = 1)
    private long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "item_name")
    private String itemName;
    @Column(name = "item_extra")
    private String itemExtra;
    @Column(name = "item_enchants")
    private String itemEnchants;
    @Column(name = "item_nbt")
    private String itemNbt;
    private long part;
    private boolean completed;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
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

    public long getPart() {
        return part;
    }

    public void setPart(long part) {
        this.part = part;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
