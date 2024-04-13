package pro.gravit.simplecabinet.web.configuration.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "registration")
public class RegistrationConfig {
    private boolean enabled;
    private RegistrationMode mode;
    private int minPasswordLength;
    private int maxPasswordLength;
    private String confirmUrl;

    public RegistrationConfig() {
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public RegistrationMode getMode() {
        return mode;
    }

    public void setMode(RegistrationMode mode) {
        this.mode = mode;
    }

    public int getMinPasswordLength() {
        return minPasswordLength;
    }

    public void setMinPasswordLength(int minPasswordLength) {
        this.minPasswordLength = minPasswordLength;
    }

    public int getMaxPasswordLength() {
        return maxPasswordLength;
    }

    public void setMaxPasswordLength(int maxPasswordLength) {
        this.maxPasswordLength = maxPasswordLength;
    }

    public String getConfirmUrl() {
        return confirmUrl;
    }

    public void setConfirmUrl(String confirmUrl) {
        this.confirmUrl = confirmUrl;
    }

    public enum RegistrationMode {
        DEFAULT, ADMIN_ACCEPT, EMAIL_CONFIRM
    }
}
