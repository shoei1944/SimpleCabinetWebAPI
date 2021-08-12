package pro.gravit.simplecabinet.web.dto;

import pro.gravit.simplecabinet.web.model.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class UserDto {
    public final long id;
    public final UUID uuid;
    public final User.Gender gender;
    public final String status;
    public final LocalDateTime registrationDate;
    public final LocalDateTime lastLoginDate;
    public final List<UserGroupDto> groups;

    public UserDto(User user) {
        this.id = user.getId();
        this.uuid = user.getUuid();
        this.gender = user.getGender();
        this.status = user.getStatus();
        this.registrationDate = user.getRegistrationDate();
        this.lastLoginDate = user.getLastLoginDate();
        this.groups = null;
    }
}
