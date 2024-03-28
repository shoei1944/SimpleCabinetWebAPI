package pro.gravit.simplecabinet.web.service.mail;

import jakarta.annotation.Priority;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@Priority(value = 0)
@ConditionalOnProperty(
        value = "mail.enabled")
public class MailSTMPService implements MailService {
    @Autowired
    public JavaMailSender emailSender;

    public void sendSimpleEmail(String toAddress, String subject, String message) {
    }
}
