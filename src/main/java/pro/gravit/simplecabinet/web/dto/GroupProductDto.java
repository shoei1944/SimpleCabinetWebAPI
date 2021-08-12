package pro.gravit.simplecabinet.web.dto;

import pro.gravit.simplecabinet.web.model.GroupProduct;

public class GroupProductDto {
    public final long id;
    public final String displayName;
    public final String description;
    public final long expireDays;
    public final boolean available;

    public GroupProductDto(GroupProduct entity) {
        this.id = entity.getId();
        this.displayName = entity.getDisplayName();
        this.description = entity.getDescription();
        this.expireDays = entity.getExpireDays();
        this.available = entity.isAvailable();
    }
}
