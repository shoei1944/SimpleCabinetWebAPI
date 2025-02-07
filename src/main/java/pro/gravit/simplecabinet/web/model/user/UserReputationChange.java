package pro.gravit.simplecabinet.web.model.user;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity(name = "UserReputationChange")
@Table(name = "user_rep_change")
public class UserReputationChange {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_rep_change_generator")
    @SequenceGenerator(name = "users_generator", sequenceName = "users_seq", allocationSize = 1)
    private long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "target_id")
    private User target;
    private Long value;
    private LocalDateTime date;
    private ReputationChangeReason reason;

    public User getTarget() {
        return target;
    }

    public void setTarget(User target) {
        this.target = target;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }

    public ReputationChangeReason getReason() {
        return reason;
    }

    public void setReason(ReputationChangeReason reason) {
        this.reason = reason;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public enum ReputationChangeReason {
        NORMAL, SET
    }
}
