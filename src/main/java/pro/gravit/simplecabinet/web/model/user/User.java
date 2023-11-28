package pro.gravit.simplecabinet.web.model.user;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity(name = "User")
@Table(name = "users")
@Cacheable
public class User implements BasicUser {

    public static HashType DEFAULT_PASSWORD_HASH = HashType.BCRYPT;
    //Base and launcher
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_generator")
    @SequenceGenerator(name = "users_generator", sequenceName = "users_seq", allocationSize = 1)
    private long id;
    @Column(unique = true)
    private String username;
    @Column(unique = true)
    private UUID uuid;
    //Password and permissions
    @Column(name = "hash_type")
    @Enumerated(EnumType.ORDINAL)
    private HashType hashType = HashType.BCRYPT;
    private String password;
    //Special
    @Column(unique = true)
    private String email;
    //Skin info
    @Column(name = "skin_model")
    private String skinModel;
    //Addional info (may be null)
    @Enumerated(EnumType.ORDINAL)
    private Gender gender;
    private String status;
    @Column(name = "registration_date")
    private LocalDateTime registrationDate;
    @Column(name = "totp_secret_key")
    private String totpSecretKey;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<UserGroup> groups;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<UserSession> sessions;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<UserAsset> assets;

    public static boolean isCorrectEmail(String email) //Very simple check
    {
        return email != null && email.contains("@") && email.length() >= 3;
    }

    public long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSkinModel() {
        return skinModel;
    }

    public void setSkinModel(String skinModel) {
        this.skinModel = skinModel;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTotpSecretKey() {
        return totpSecretKey;
    }

    public void setTotpSecretKey(String totpSecretKey) {
        this.totpSecretKey = totpSecretKey;
    }

    public List<UserGroup> getGroups() {
        return groups;
    }

    public void setGroups(List<UserGroup> groups) {
        this.groups = groups;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setRawPassword(String password) {
        this.password = password;
    }

    public void setRawPasswordType(HashType type) {
        this.hashType = type;
    }

    public String getPassword() {
        return password;
    }

    public HashType getHashType() {
        return hashType;
    }

    public void setHashType(HashType hashType) {
        this.hashType = hashType;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDateTime registrationDate) {
        this.registrationDate = registrationDate;
    }

    public List<UserAsset> getAssets() {
        return assets;
    }

    public void setAssets(List<UserAsset> assets) {
        this.assets = assets;
    }

    public enum HashType {
        BCRYPT(true),
        DOUBLEMD5(false),
        MD5(false),
        SHA256(false),
        AUTHMESHA256(true),
        PHPASS(true);
        boolean trusted;

        HashType(boolean trusted) {
            this.trusted = trusted;
        }

        public boolean isTrusted() {
            return trusted;
        }
    }

    public enum Gender {
        FEMALE,
        MALE
    }
}
