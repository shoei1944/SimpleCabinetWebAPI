package pro.gravit.simplecabinet.web.controller.payment;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pro.gravit.simplecabinet.web.service.payment.FreekassaPaymentService;

import static pro.gravit.simplecabinet.web.controller.payment.YooWebhookController.matches;

@RestController
@RequestMapping("/webhooks/freekassa")
public class FreekassaWebhookController {
    @Autowired
    private FreekassaPaymentService service;

    @PostMapping("/payment")
    public String payment(@RequestBody FreekassaPaymentService.WebhookResponse webhookResponse, HttpServletRequest request) {
        String ip = request.getRemoteAddr();
        if (!matches(ip, "168.119.157.136") && !matches(ip, "168.119.60.227") && !matches(ip, "138.201.88.124") && !matches(ip, "178.154.197.79")) {
            throw new SecurityException("Access denied (IP address)");
        }
        service.complete(webhookResponse);
        return "YES";
    }
}
