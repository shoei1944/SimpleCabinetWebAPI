package pro.gravit.simplecabinet.web.service.payment;

import pro.gravit.simplecabinet.web.model.User;
import pro.gravit.simplecabinet.web.service.PaymentService;

public interface BasicPaymentService {
    PaymentService.PaymentCreationInfo createBalancePayment(User user, double sum) throws Exception;
}
