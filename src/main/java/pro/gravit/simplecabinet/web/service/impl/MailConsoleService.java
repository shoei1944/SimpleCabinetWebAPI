package pro.gravit.simplecabinet.web.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.gravit.simplecabinet.web.service.MailService;

import javax.annotation.Priority;

@Service
@Priority(value = 0)
public class MailConsoleService implements MailService {
    private transient final Logger logger = LoggerFactory.getLogger(MailConsoleService.class);

    @Override
    public void sendSimpleEmail(String toAddress, String subject, String message) {
        logger.info("Mail {} {} {}", toAddress, subject, message);
    }
}
