package pro.gravit.simplecabinet.web.dto;

import pro.gravit.simplecabinet.web.model.UserPayment;

public class UserPaymentDto {
    public final long id;
    public final String system;
    public final String systemPaymentId;
    public final double sum;

    public UserPaymentDto(UserPayment entity) {
        this.id = entity.getId();
        this.system = entity.getSystem();
        this.systemPaymentId = entity.getSystemPaymentId();
        this.sum = entity.getSum();
    }
}
