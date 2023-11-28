package pro.gravit.simplecabinet.web.service.captcha;

import org.springframework.stereotype.Service;

import javax.annotation.Priority;

@Service
@Priority(value = 1)
public class NoCaptchaService implements CaptchaService {
    @Override
    public boolean verify(String captchaResponse) {
        return true;
    }
}
