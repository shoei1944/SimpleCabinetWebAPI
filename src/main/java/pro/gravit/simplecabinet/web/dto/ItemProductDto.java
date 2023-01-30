package pro.gravit.simplecabinet.web.dto;

import pro.gravit.simplecabinet.web.model.ItemProduct;
import pro.gravit.simplecabinet.web.model.Product;

public class ItemProductDto {
    public final long id;
    public final double price;
    public final String currency;
    public final String displayName;
    public final String description;
    public final String pictureUrl;
    // Limitations
    public final Product.ProductLimitations limitations;

    public ItemProductDto(ItemProduct entity) {
        this.id = entity.getId();
        this.price = entity.getPrice();
        this.currency = entity.getCurrency();
        this.displayName = entity.getDisplayName();
        this.description = entity.getDescription();
        this.pictureUrl = entity.getPictureUrl();
        this.limitations = entity.getLimitations();
    }
}
