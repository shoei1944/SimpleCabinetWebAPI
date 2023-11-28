package pro.gravit.simplecabinet.web.model.user;

import javax.persistence.*;

@Entity
@Table(name = "balance")
public class UserBalance {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "balance_generator")
    @SequenceGenerator(name = "balance_generator", sequenceName = "balance_seq", allocationSize = 1)
    private long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    private double balance;
    private String currency;

    public long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
