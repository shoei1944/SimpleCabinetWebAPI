package pro.gravit.simplecabinet.web.controller.payment;

import com.qiwi.billpayments.sdk.model.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pro.gravit.simplecabinet.web.service.payment.QiwiPaymentService;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/webhooks/qiwi")
public class QiwiWebhookController {
    @Autowired
    private QiwiPaymentService service;

    @PostMapping("/payment")
    public void payment(@RequestBody Notification notification, HttpServletRequest httpServletRequest) {
        String signature = httpServletRequest.getHeader("X-Api-Signature-SHA256");
        service.complete(notification, signature);
    }
}
