package pro.gravit.simplecabinet.web.service.payment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pro.gravit.simplecabinet.web.configuration.properties.TestPaymentConfig;
import pro.gravit.simplecabinet.web.model.shop.Payment;
import pro.gravit.simplecabinet.web.model.user.User;
import pro.gravit.simplecabinet.web.service.shop.PaymentService;
import pro.gravit.simplecabinet.web.service.user.UserService;

import java.net.http.HttpClient;

@Service
public class TestPaymentService implements BasicPaymentService {
    private final transient HttpClient client = HttpClient.newBuilder().build();
    @Autowired
    private PaymentService paymentService;
    @Autowired
    private UserService userService;
    @Autowired
    private TestPaymentConfig config;

    @Override
    public PaymentService.PaymentCreationInfo createBalancePayment(User user, double sum, String ip) throws Exception {
        var payment = paymentService.createBasic(user, sum);
        payment.setSystem("Test");
        payment.setSystemPaymentId(String.valueOf(payment.getId()));
        paymentService.save(payment);
        return new PaymentService.PaymentCreationInfo(new PaymentService.PaymentRedirectInfo(String.format("%s?id=%s&current=%f", config.getUrl(), payment.getSystemPaymentId(), payment.getSum())), payment);
    }

    public void complete(WebhookResponse webhookResponse) {
        var payment = paymentService.findUserPaymentBySystemId("Test", webhookResponse.id()).orElseThrow();
        var oldStatus = payment.getStatus();
        if (webhookResponse.status().equalsIgnoreCase("SUCCESS")) {
            completePayment(payment, Payment.PaymentStatus.SUCCESS);
            if (oldStatus != Payment.PaymentStatus.SUCCESS) {
                paymentService.deliveryPayment(payment);
            }
        } else {
            completePayment(payment, Payment.PaymentStatus.CANCELED);
        }
    }

    private void completePayment(Payment payment, Payment.PaymentStatus status) {
        payment.setStatus(status);
        paymentService.save(payment);
    }

    public record WebhookResponse(String id, String current, String status, boolean sending_status) {

    }
}
