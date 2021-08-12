package pro.gravit.simplecabinet.web.dto;

import pro.gravit.simplecabinet.web.model.GroupOrder;

public class GroupOrderDto extends OrderDto {
    public final String server;

    public GroupOrderDto(GroupOrder entity) {
        super(entity);
        server = entity.getServer();
    }
}
