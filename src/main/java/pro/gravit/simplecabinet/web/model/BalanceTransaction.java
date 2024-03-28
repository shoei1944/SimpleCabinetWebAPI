package pro.gravit.simplecabinet.web.model;

import jakarta.persistence.*;
import pro.gravit.simplecabinet.web.model.user.User;
import pro.gravit.simplecabinet.web.model.user.UserBalance;

import java.time.LocalDateTime;

@Entity(name = "BalanceTransaction")
@Table(name = "balance_transactions")
public class BalanceTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "balance_transactions_generator")
    @SequenceGenerator(name = "balance_transactions_generator", sequenceName = "balance_transactions_seq", allocationSize = 1)
    private long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "from_id")
    private UserBalance from;
    @ManyToOne
    @JoinColumn(name = "to_id")
    private UserBalance to;
    @Column(name = "from_count")
    private double fromCount;
    @Column(name = "to_count")
    private double toCount;
    private boolean multicurrency;
    private String comment;
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public UserBalance getFrom() {
        return from;
    }

    public void setFrom(UserBalance from) {
        this.from = from;
    }

    public UserBalance getTo() {
        return to;
    }

    public void setTo(UserBalance to) {
        this.to = to;
    }

    public double getFromCount() {
        return fromCount;
    }

    public void setFromCount(double fromCount) {
        this.fromCount = fromCount;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public double getToCount() {
        return toCount;
    }

    public void setToCount(double toCount) {
        this.toCount = toCount;
    }

    public boolean isMulticurrency() {
        return multicurrency;
    }

    public void setMulticurrency(boolean multicurrency) {
        this.multicurrency = multicurrency;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
