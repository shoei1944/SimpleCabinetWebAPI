package pro.gravit.simplecabinet.web.configuration.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "payment.freekassa")
public class FreekassaPaymentConfig {
    public String shopId;
    public String apiKey;
    public String paymentSystemId;
    public String secret;
    public boolean test;

    public FreekassaPaymentConfig() {
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public boolean isTest() {
        return test;
    }

    public void setTest(boolean test) {
        this.test = test;
    }

    public String getPaymentSystemId() {
        return paymentSystemId;
    }

    public void setPaymentSystemId(String paymentSystemId) {
        this.paymentSystemId = paymentSystemId;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }
}
