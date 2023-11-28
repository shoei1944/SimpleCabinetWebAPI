package pro.gravit.simplecabinet.web.service.mail;

public interface MailService {
    void sendSimpleEmail(String toAddress, String subject, String message);
}
