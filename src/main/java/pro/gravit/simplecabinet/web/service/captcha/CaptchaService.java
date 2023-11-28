package pro.gravit.simplecabinet.web.service.captcha;

public interface CaptchaService {
    boolean verify(String captchaResponse);
}
