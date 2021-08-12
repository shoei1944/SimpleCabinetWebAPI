package pro.gravit.simplecabinet.web.dto;

import pro.gravit.simplecabinet.web.model.ItemProduct;
import pro.gravit.simplecabinet.web.model.Product;

public class ItemProductDto {
    public final long id;
    public final String displayName;
    public final String description;
    public final String pictureUrl;
    // Limitations
    public final Product.ProductLimitations limitations;

    public ItemProductDto(ItemProduct entity) {
        this.id = entity.getId();
        this.displayName = entity.getDisplayName();
        this.description = entity.getDescription();
        this.pictureUrl = entity.getPictureUrl();
        this.limitations = entity.getLimitations();
    }
}
