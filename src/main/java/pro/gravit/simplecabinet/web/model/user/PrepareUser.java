package pro.gravit.simplecabinet.web.model.user;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "prepare_users")
public class PrepareUser {
    @Column(unique = true)
    public String username;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_generator")
    @SequenceGenerator(name = "prepare_users_generator", sequenceName = "prepare_users_seq", allocationSize = 1)
    private long id;
    @Column(unique = true)
    private String email;
    private String password;
    @Column(name = "hash_type")
    @Enumerated(EnumType.ORDINAL)
    private User.HashType hashType = User.HashType.BCRYPT;
    @Column(name = "confirm_token")
    private String confirmToken;
    private LocalDateTime date;

    public long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User.HashType getHashType() {
        return hashType;
    }

    public void setHashType(User.HashType hashType) {
        this.hashType = hashType;
    }

    public String getConfirmToken() {
        return confirmToken;
    }

    public void setConfirmToken(String confirmToken) {
        this.confirmToken = confirmToken;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
