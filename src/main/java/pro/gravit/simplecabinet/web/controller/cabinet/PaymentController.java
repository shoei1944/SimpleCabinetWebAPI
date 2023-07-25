package pro.gravit.simplecabinet.web.controller.cabinet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import pro.gravit.simplecabinet.web.dto.PageDto;
import pro.gravit.simplecabinet.web.dto.UserPaymentDto;
import pro.gravit.simplecabinet.web.exception.InvalidParametersException;
import pro.gravit.simplecabinet.web.service.PaymentService;
import pro.gravit.simplecabinet.web.service.UserService;
import pro.gravit.simplecabinet.web.service.payment.*;

import javax.servlet.http.HttpServletRequest;

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
    private FreekassaPaymentService freekassaPaymentService;
    @Autowired
    private TestPaymentService testPaymentService;
    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public PaymentInfoDto paymentCreate(@RequestBody BalancePaymentCreateRequest request, HttpServletRequest servletRequest) throws Exception {
        PaymentService.PaymentCreationInfo info;
        var user = userService.getCurrentUser();
        var ref = user.getReference();
        BasicPaymentService basicPaymentService = switch (request.system) {
            case "Yoo" -> yooPaymentService;
            case "Qiwi" -> qiwiPaymentService;
            case "Freekassa" -> freekassaPaymentService;
            case "Test" -> testPaymentService;
            default -> throw new InvalidParametersException("Payment system not found", 11);
        };
        info = basicPaymentService.createBalancePayment(ref, request.sum, servletRequest.getRemoteAddr());
        return new PaymentInfoDto(info.redirect(), new UserPaymentDto(info.paymentInfo()));
    }

    @GetMapping("/page/{pageId}")
    public PageDto<UserPaymentDto> getPage(@PathVariable int pageId) {
        var user = userService.getCurrentUser();
        var list = paymentService.findAllByUser(user.getReference(), PageRequest.of(pageId, 10));
        return new PageDto<>(list.map(UserPaymentDto::new));
    }

    public static record PaymentInfoDto(PaymentService.PaymentRedirectInfo redirect, UserPaymentDto payment) {

    }

    public static record BalancePaymentCreateRequest(String system, double sum) {

    }
}
