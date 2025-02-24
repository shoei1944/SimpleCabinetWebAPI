package pro.gravit.simplecabinet.web.model.user;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Entity(name = "UserPermission")
@Table(name = "user_permissions")
public class UserPermission {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_permissions_generator")
    @SequenceGenerator(name = "user_permissions_generator", sequenceName = "user_permissions_seq", allocationSize = 1)
    private long id;
    @Setter
    @Column(name = "group_name")
    private String groupName;
    @Setter
    private String name;
    @Setter
    private String value;

    public UserPermission() {
    }

    public UserPermission(String groupName, String name, String value) {
        this.groupName = groupName;
        this.name = name;
        this.value = value;
    }

}
