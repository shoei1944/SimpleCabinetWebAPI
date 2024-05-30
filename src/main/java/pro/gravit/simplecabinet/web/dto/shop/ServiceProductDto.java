package pro.gravit.simplecabinet.web.dto.shop;

import pro.gravit.simplecabinet.web.model.shop.Product;

public class ServiceProductDto {
    public final long id;
    public final double price;
    public final boolean stackable;
    public final String currency;
    public final String displayName;
    public final String description;
    public final String pictureUrl;
    // Limitations
    public final Product.ProductLimitations limitations;

    public ServiceProductDto(long id, double price, boolean stackable, String currency, String displayName, String description, String pictureUrl, Product.ProductLimitations limitations) {
        this.id = id;
        this.price = price;
        this.stackable = stackable;
        this.currency = currency;
        this.displayName = displayName;
        this.description = description;
        this.pictureUrl = pictureUrl;
        this.limitations = limitations;
    }
}
