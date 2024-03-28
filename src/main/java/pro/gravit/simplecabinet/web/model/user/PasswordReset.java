package pro.gravit.simplecabinet.web.model.user;

import jakarta.persistence.*;

import java.util.UUID;

@Entity(name = "PasswordReset")
@Table(name = "password_resets")
public class PasswordReset {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "password_resets_generator")
    @SequenceGenerator(name = "password_resets_generator", sequenceName = "password_resets_seq", allocationSize = 1)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(unique = true)
    private UUID uuid;


    public long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }
}
