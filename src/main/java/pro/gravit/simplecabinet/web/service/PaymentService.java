package pro.gravit.simplecabinet.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pro.gravit.simplecabinet.web.exception.BalanceException;
import pro.gravit.simplecabinet.web.model.User;
import pro.gravit.simplecabinet.web.model.UserPayment;
import pro.gravit.simplecabinet.web.repository.UserPaymentRepository;

import java.util.Optional;

@Service
public class PaymentService {
    @Autowired
    private UserPaymentRepository paymentRepository;
    @Autowired
    private BalanceService balanceService;

    public UserPayment createBasic(User user, double sum) {
        var payment = new UserPayment();
        payment.setUser(user);
        payment.setSum(sum);
        payment.setStatus(UserPayment.PaymentStatus.INITIATED);
        paymentRepository.save(payment);
        return payment;
    }

    public <S extends UserPayment> S save(S entity) {
        return paymentRepository.save(entity);
    }

    public Optional<UserPayment> findUserPaymentBySystemId(String system, String systemPaymentId) {
        return paymentRepository.findUserPaymentBySystemAndSystemPaymentId(system, systemPaymentId);
    }

    @Transactional
    public void deliveryPayment(UserPayment payment) throws BalanceException {
        System.out.printf("Delivery #%d sum %f", payment.getId(), payment.getSum());
        var user = payment.getUser();
        var balance = balanceService.findOrCreateUserBalanceByUserAndCurrency(user, "DONATE");
        balanceService.addMoney(balance, payment.getSum(), String.format("Payment %d", payment.getId()));
    }

    public static record PaymentRedirectInfo(String url) {
    }

    public static record PaymentCreationInfo(PaymentRedirectInfo redirect, UserPayment paymentInfo) {
    }
}
