package pro.gravit.simplecabinet.web.service;

import dev.samstevens.totp.code.*;
import dev.samstevens.totp.qr.QrData;
import dev.samstevens.totp.secret.DefaultSecretGenerator;
import dev.samstevens.totp.secret.SecretGenerator;
import dev.samstevens.totp.time.SystemTimeProvider;
import dev.samstevens.totp.time.TimeProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pro.gravit.simplecabinet.web.model.BasicUser;
import pro.gravit.simplecabinet.web.model.User;
import pro.gravit.simplecabinet.web.repository.UserRepository;

@Service
public class PasswordCheckService {
    private final PasswordEncoder bcryptEncoder = new BCryptPasswordEncoder();
    private final TimeProvider timeProvider = new SystemTimeProvider();
    private final CodeGenerator codeGenerator = new DefaultCodeGenerator();
    private final CodeVerifier verifier = new DefaultCodeVerifier(codeGenerator, timeProvider);
    private final SecretGenerator secretGenerator = new DefaultSecretGenerator(64);
    @Autowired
    private UserRepository repository;

    public boolean checkPassword(User user, String password) {
        User.HashType type = user.getHashType();
        switch (type) {

            case BCRYPT -> {
                return bcryptEncoder.matches(password, user.getPassword());
            }
            case DOUBLEMD5 -> {
            }
            case MD5 -> {
            }
            case SHA256 -> {
            }
            case AUTHMESHA256 -> {
            }
            case PHPASS -> {
            }
        }
        return false;
    }

    public boolean checkTotpPassword(User user, String totpPassword) {
        return verifier.isValidCode(user.getTotpSecretKey(), totpPassword);
    }

    public boolean checkTotpPassword(String secret, String totpPassword) {
        return verifier.isValidCode(secret, totpPassword);
    }

    public String makeQrCodeUri(BasicUser user, String totpSecret) {
        QrData data = new QrData.Builder()
                .label(user.getUsername())
                .secret(totpSecret)
                .issuer("SimpleCabinet2")
                .algorithm(HashingAlgorithm.SHA1) // More on this below
                .digits(6)
                .period(30)
                .build();
        return data.getUri();
    }

    public String newTotpSecret() {
        return secretGenerator.generate();
    }

    public void setPassword(User user, String password) {
        user.setHashType(User.HashType.BCRYPT);
        user.setRawPassword(bcryptEncoder.encode(password));
    }
}
