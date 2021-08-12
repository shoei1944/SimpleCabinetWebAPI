package pro.gravit.simplecabinet.web.dto;

import pro.gravit.simplecabinet.web.model.UserGroup;

public class UserGroupDto {
    public final long id;
    public final String groupName;

    public UserGroupDto(UserGroup entity) {
        this.id = entity.getId();
        this.groupName = entity.getGroupName();
    }
}
