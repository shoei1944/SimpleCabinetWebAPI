package pro.gravit.simplecabinet.web.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "UserGroup")
@Table(name = "user_groups")
public class UserGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_groups_generator")
    @SequenceGenerator(name = "user_groups_generator", sequenceName = "user_groups_seq", allocationSize = 1)
    private long id;
    private String groupName;
    private long priority;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public long getId() {
        return id;
    }

    public String getGroupName() {
        return groupName;
    }

    public long getPriority() {
        return priority;
    }

    public void setPriority(long priority) {
        this.priority = priority;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
