package pro.gravit.simplecabinet.web.model;

import javax.persistence.*;

@Entity(name = "Server")
@Table(name = "servers")
public class Server {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "servers_generator")
    @SequenceGenerator(name = "servers_generator", sequenceName = "servers_seq", allocationSize = 1)
    private long id;
    private String ip;
    private int port;
    @Column(name = "max_online")
    private int maxOnline;
    private int online;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
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
}
