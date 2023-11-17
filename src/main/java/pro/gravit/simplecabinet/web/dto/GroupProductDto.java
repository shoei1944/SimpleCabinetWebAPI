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

    public GroupProductDto(long id, double price, String currency, String displayName, String description, String pictureUrl, long expireDays, boolean available) {
        this.id = id;
        this.price = price;
        this.currency = currency;
        this.displayName = displayName;
        this.description = description;
        this.pictureUrl = pictureUrl;
        this.expireDays = expireDays;
        this.available = available;
    }
}
