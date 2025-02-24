package pro.gravit.simplecabinet.web.model.shop;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "group_products")
public class GroupProduct extends Product {
    private String name;
    private String server;
    private String world;
    private String context;
    @Column(name = "expire_days")
    private long expireDays;
    private String localName;
    private boolean local;
    private boolean stackable;

}
