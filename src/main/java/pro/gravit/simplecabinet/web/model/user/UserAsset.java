package pro.gravit.simplecabinet.web.model.user;

import jakarta.persistence.*;

@Entity(name = "UserAsset")
@Table(name = "user_assets")
public class UserAsset {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_assets_generator")
    @SequenceGenerator(name = "user_assets_generator", sequenceName = "user_assets_seq", allocationSize = 1)
    private long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    private String name;
    private String hash;
    private String metadata;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }
}
