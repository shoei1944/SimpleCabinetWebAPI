package pro.gravit.simplecabinet.web.model.user;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Entity(name = "UserGroup")
@Table(name = "user_groups")
public class UserGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_groups_generator")
    @SequenceGenerator(name = "user_groups_generator", sequenceName = "user_groups_seq", allocationSize = 1)
    private long id;
    @Setter
    private String groupName;
    @Setter
    private long priority;
    @Setter
    private LocalDateTime startDate;
    @Setter
    private LocalDateTime endDate;
    @Setter
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
