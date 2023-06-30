package pro.gravit.simplecabinet.web.service.payment;

import com.qiwi.billpayments.sdk.client.BillPaymentClient;
import com.qiwi.billpayments.sdk.client.BillPaymentClientFactory;
import com.qiwi.billpayments.sdk.exception.BillPaymentServiceException;
import com.qiwi.billpayments.sdk.model.MoneyAmount;
import com.qiwi.billpayments.sdk.model.Notification;
import com.qiwi.billpayments.sdk.model.in.CreateBillInfo;
import com.qiwi.billpayments.sdk.model.in.Customer;
import com.qiwi.billpayments.sdk.utils.BillPaymentsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pro.gravit.simplecabinet.web.configuration.properties.QiwiPaymentConfig;
import pro.gravit.simplecabinet.web.exception.BalanceException;
import pro.gravit.simplecabinet.web.exception.PaymentException;
import pro.gravit.simplecabinet.web.model.User;
import pro.gravit.simplecabinet.web.model.UserPayment;
import pro.gravit.simplecabinet.web.service.PaymentService;

import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.time.ZonedDateTime;
import java.util.Currency;
import java.util.UUID;

@Service
public class QiwiPaymentService implements BasicPaymentService {
    @Autowired
    private PaymentService paymentService;
    @Autowired
    private QiwiPaymentConfig config;

    public PaymentService.PaymentCreationInfo createBalancePayment(User user, double sum) throws URISyntaxException {
        var payment = paymentService.createBasic(user, sum);
        payment.setSystem("Qiwi");
        BillPaymentClient client = BillPaymentClientFactory.createDefault(config.secretKey);
        var request = new CreateBillInfo(
                UUID.randomUUID().toString(),
                new MoneyAmount(
                        BigDecimal.valueOf(sum),
                        Currency.getInstance("RUB")
                ),
                "Balance",
                ZonedDateTime.now().plusDays(45),
                new Customer(
                        user.getEmail(),
                        Long.toString(user.getId()),
                        null
                ),
                config.redirectUrl
        );
        try {
            var response = client.createBill(request);
            payment.setSystemPaymentId(response.getBillId());
            paymentService.save(payment);
            return new PaymentService.PaymentCreationInfo(new PaymentService.PaymentRedirectInfo(response.getPayUrl()), payment);
        } catch (BillPaymentServiceException e) {
            payment.setStatus(UserPayment.PaymentStatus.CANCELED);
            paymentService.save(payment);
            throw new PaymentException(e.getResponse().getDescription(), 4);
        }
    }

    public void complete(Notification notification, String signature) throws BalanceException {
        if (!BillPaymentsUtils.checkNotificationSignature(signature, notification, config.secretKey)) {
            throw new SecurityException("Invalid signature");
        }
        String id = notification.getBill().getBillId();
        var payment = paymentService.findUserPaymentBySystemId("Qiwi", id).orElseThrow();
        if (payment.getStatus() == UserPayment.PaymentStatus.SUCCESS) {
            return;
        }
        switch (notification.getBill().getStatus()) {
            case WAITING -> {
            }
            case PAID -> {
                var oldStatus = payment.getStatus();
                completePayment(payment, UserPayment.PaymentStatus.SUCCESS);
                if (oldStatus != UserPayment.PaymentStatus.SUCCESS) {
                    paymentService.deliveryPayment(payment);
                }
            }
            case REJECTED -> {
                completePayment(payment, UserPayment.PaymentStatus.ERROR);
            }
            case EXPIRED -> {
                completePayment(payment, UserPayment.PaymentStatus.CANCELED);
            }
        }
    }

    private void completePayment(UserPayment payment, UserPayment.PaymentStatus status) {
        payment.setStatus(status);
        paymentService.save(payment);
    }
}
