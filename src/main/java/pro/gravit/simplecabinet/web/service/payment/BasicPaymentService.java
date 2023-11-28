package pro.gravit.simplecabinet.web.service.payment;

import pro.gravit.simplecabinet.web.model.user.User;
import pro.gravit.simplecabinet.web.service.shop.PaymentService;

public interface BasicPaymentService {
    boolean isEnabled();
    PaymentService.PaymentCreationInfo createBalancePayment(User user, double sum, String ip) throws Exception;
}
