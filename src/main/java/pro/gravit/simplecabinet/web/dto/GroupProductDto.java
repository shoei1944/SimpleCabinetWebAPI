package pro.gravit.simplecabinet.web.dto;

import pro.gravit.simplecabinet.web.model.GroupProduct;

public class GroupProductDto {
    public final long id;
    public final double price;
    public final String currency;
    public final String displayName;
    public final String description;
    public final String pictureUrl;
    public final long expireDays;
    public final boolean available;

    public GroupProductDto(GroupProduct entity) {
        this.id = entity.getId();
        this.price = entity.getPrice();
        this.currency = entity.getCurrency();
        this.displayName = entity.getDisplayName();
        this.description = entity.getDescription();
        this.pictureUrl = entity.getPictureUrl();
        this.expireDays = entity.getExpireDays();
        this.available = entity.isAvailable();
    }
}
