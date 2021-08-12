package pro.gravit.simplecabinet.web.service;

public interface MailService {
    void sendSimpleEmail(String toAddress, String subject, String message);
}
