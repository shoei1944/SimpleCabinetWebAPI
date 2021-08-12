package pro.gravit.simplecabinet.web.controller.cabinet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pro.gravit.simplecabinet.web.dto.UserPaymentDto;
import pro.gravit.simplecabinet.web.exception.InvalidParametersException;
import pro.gravit.simplecabinet.web.service.PaymentService;
import pro.gravit.simplecabinet.web.service.UserService;
import pro.gravit.simplecabinet.web.service.payment.QiwiPaymentService;
import pro.gravit.simplecabinet.web.service.payment.YooPaymentService;

import java.io.IOException;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/cabinet/payment")
public class PaymentController {
    @Autowired
    private PaymentService paymentService;
    @Autowired
    private YooPaymentService yooPaymentService;
    @Autowired
    private QiwiPaymentService qiwiPaymentService;
    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public PaymentInfoDto paymentCreate(@RequestBody BalancePaymentCreateRequest request) throws IOException, InterruptedException, URISyntaxException {
        PaymentService.PaymentCreationInfo info;
        var user = userService.getCurrentUser();
        var ref = user.getReference();
        info = switch (request.system) {
            case "Yoo" -> yooPaymentService.createBalancePayment(ref, request.sum);
            case "Qiwi" -> qiwiPaymentService.createBalancePayment(ref, request.sum);
            default -> throw new InvalidParametersException("Payment system not found", 11);
        };
        return new PaymentInfoDto(info.redirect(), new UserPaymentDto(info.paymentInfo()));
    }

    public static record PaymentInfoDto(PaymentService.PaymentRedirectInfo redirect, UserPaymentDto payment) {

    }

    public static record BalancePaymentCreateRequest(String system, double sum) {

    }
}
