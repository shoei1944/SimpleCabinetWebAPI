package pro.gravit.simplecabinet.web.model;

import javax.persistence.*;

@Entity(name = "UserPermission")
@Table(name = "user_permissions")
public class UserPermission {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_permissions_generator")
    @SequenceGenerator(name = "user_permissions_generator", sequenceName = "user_permissions_seq", allocationSize = 1)
    private long id;
    @Column(name = "group_name")
    private String groupName;
    private String name;
    private String value;

    public UserPermission() {
    }

    public UserPermission(String groupName, String name, String value) {
        this.groupName = groupName;
        this.name = name;
        this.value = value;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
