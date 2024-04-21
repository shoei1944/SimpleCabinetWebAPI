package pro.gravit.simplecabinet.web.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity(name = "Server")
@Table(name = "servers")
public class Server {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "servers_generator")
    @SequenceGenerator(name = "servers_generator", sequenceName = "servers_seq", allocationSize = 1)
    private long id;
    private String name;
    private String displayName;
    @Column(name = "max_online")
    private int maxOnline;
    private int online;
    private int tps;
    private List<String> users;
    @Column(name = "update_date")
    private LocalDateTime updateDate;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getMaxOnline() {
        return maxOnline;
    }

    public void setMaxOnline(int maxOnline) {
        this.maxOnline = maxOnline;
    }

    public int getOnline() {
        return online;
    }

    public void setOnline(int online) {
        this.online = online;
    }

    public List<String> getUsers() {
        return users;
    }

    public void setUsers(List<String> users) {
        this.users = users;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public LocalDateTime getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDateTime updateDate) {
        this.updateDate = updateDate;
    }

    public int getTps() {
        return tps;
    }

    public void setTps(int tps) {
        this.tps = tps;
    }
}
