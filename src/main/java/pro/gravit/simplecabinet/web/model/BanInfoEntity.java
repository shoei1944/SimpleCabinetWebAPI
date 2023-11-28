package pro.gravit.simplecabinet.web.model;

import pro.gravit.simplecabinet.web.model.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "BanInfo")
@Table(name = "baninfo")
public class BanInfoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "baninfo_generator")
    @SequenceGenerator(name = "baninfo_generator", sequenceName = "baninfo_seq", allocationSize = 1)
    private long id;
    @ManyToOne
    @JoinColumn(name = "target_id")
    private User target;
    @ManyToOne
    @JoinColumn(name = "moderator_id")
    private User moderator;
    private String reason;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "end_at")
    private LocalDateTime endAt;
    private boolean shadow;

    public long getId() {
        return id;
    }

    public User getTarget() {
        return target;
    }

    public void setTarget(User target) {
        this.target = target;
    }

    public User getModerator() {
        return moderator;
    }

    public void setModerator(User moderator) {
        this.moderator = moderator;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getEndAt() {
        return endAt;
    }

    public void setEndAt(LocalDateTime endAt) {
        this.endAt = endAt;
    }

    public boolean isShadow() {
        return shadow;
    }

    public void setShadow(boolean shadow) {
        this.shadow = shadow;
    }
}
