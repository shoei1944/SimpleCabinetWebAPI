package pro.gravit.simplecabinet.web.model.shop;

import pro.gravit.simplecabinet.web.model.user.User;

import javax.persistence.*;

@Entity
@Table(name = "payments")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "payments_generator")
    @SequenceGenerator(name = "payments_generator", sequenceName = "payments_seq", allocationSize = 1)
    private long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    private double sum;
    private String system;
    @Column(name = "system_payment_id")
    private String systemPaymentId;
    private PaymentStatus status;

    public long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

    public String getSystem() {
        return system;
    }

    public void setSystem(String system) {
        this.system = system;
    }

    public String getSystemPaymentId() {
        return systemPaymentId;
    }

    public void setSystemPaymentId(String systemPaymentId) {
        this.systemPaymentId = systemPaymentId;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }

    public enum PaymentStatus {
        INITIATED, SUCCESS, CANCELED, ERROR
    }
}
