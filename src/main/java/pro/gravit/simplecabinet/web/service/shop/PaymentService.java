package pro.gravit.simplecabinet.web.service.shop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pro.gravit.simplecabinet.web.exception.BalanceException;
import pro.gravit.simplecabinet.web.model.shop.Payment;
import pro.gravit.simplecabinet.web.model.user.User;
import pro.gravit.simplecabinet.web.repository.shop.PaymentRepository;
import pro.gravit.simplecabinet.web.service.user.BalanceService;

import java.util.Optional;

@Service
public class PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private BalanceService balanceService;
    @Value("${payment.currency}")
    private String currency;

    public Payment createBasic(User user, double sum) {
        var payment = new Payment();
        payment.setUser(user);
        payment.setSum(sum);
        payment.setStatus(Payment.PaymentStatus.INITIATED);
        paymentRepository.save(payment);
        return payment;
    }

    public Page<Payment> findAllByUser(User user, Pageable pageable) {
        return paymentRepository.findAllByUser(user, pageable);
    }

    public <S extends Payment> S save(S entity) {
        return paymentRepository.save(entity);
    }

    public Optional<Payment> findUserPaymentBySystemId(String system, String systemPaymentId) {
        return paymentRepository.findUserPaymentBySystemAndSystemPaymentId(system, systemPaymentId);
    }

    @Transactional
    public void deliveryPayment(Payment payment) throws BalanceException {
        System.out.printf("Delivery #%d sum %f", payment.getId(), payment.getSum());
        var user = payment.getUser();
        var balance = balanceService.findOrCreateUserBalanceByUserAndCurrency(user, currency);
        balanceService.addMoney(balance, payment.getSum(), String.format("Payment %d", payment.getId()));
    }

    public record PaymentRedirectInfo(String url) {
    }

    public record PaymentCreationInfo(PaymentRedirectInfo redirect, Payment paymentInfo) {
    }
}
