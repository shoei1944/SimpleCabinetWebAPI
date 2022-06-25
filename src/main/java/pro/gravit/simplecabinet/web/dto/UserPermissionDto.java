package pro.gravit.simplecabinet.web.dto;

import pro.gravit.simplecabinet.web.model.UserPermission;

public class UserPermissionDto {
    public long id;
    public String groupName;
    public String name;
    public String value;

    public UserPermissionDto(UserPermission obj) {
        this.id = obj.getId();
        this.groupName = obj.getGroupName();
        this.name = obj.getName();
        this.value = obj.getValue();
    }
}
