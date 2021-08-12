package pro.gravit.simplecabinet.web.dto;

import pro.gravit.simplecabinet.web.model.ProductEnchantEntity;

public class ProductEnchantDto {
    public final long id;
    public final String name;
    public final int value;

    public ProductEnchantDto(ProductEnchantEntity entity) {
        this.id = entity.id;
        this.name = entity.name;
        this.value = entity.value;
    }
}
