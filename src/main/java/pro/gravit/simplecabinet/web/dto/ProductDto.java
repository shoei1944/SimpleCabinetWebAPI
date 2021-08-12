package pro.gravit.simplecabinet.web.dto;

import pro.gravit.simplecabinet.web.model.ProductEntity;

import java.time.LocalDateTime;

public class ProductDto {
    public final long id;
    public final ProductEntity.ProductType type;
    public final String name;
    public final String description;
    public final double price;
    //Limitations
    public final long count;
    public final LocalDateTime endDate;
    public final boolean allowStack;
    public final boolean visible;
    public final String pictureUrl;

    public ProductDto(ProductEntity entity) {
        this.id = entity.getId();
        this.type = entity.getType();
        this.name = entity.getName();
        this.description = entity.getDescription();
        this.price = entity.getPrice();
        this.count = entity.getCount();
        this.endDate = entity.getEndDate();
        this.allowStack = entity.isAllowStack();
        this.visible = entity.isVisible();
        this.pictureUrl = entity.getPictureUrl();
    }
}
