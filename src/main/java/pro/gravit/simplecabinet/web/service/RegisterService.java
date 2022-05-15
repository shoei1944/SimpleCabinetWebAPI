package pro.gravit.simplecabinet.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pro.gravit.simplecabinet.web.exception.InvalidParametersException;
import pro.gravit.simplecabinet.web.model.PrepareUser;
import pro.gravit.simplecabinet.web.model.User;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;
import java.util.regex.Pattern;

@Service
public class RegisterService {
    private final Pattern USERNAME_PATTERN = Pattern.compile("[A-Za-z0-9_-]+");
    @Autowired
    private PrepareUserService prepareUserService;
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordCheckService passwordCheckService;
    @Value("${registration.enabled}")
    private boolean enabled;
    @Value("${registration.prepare}")
    private boolean prepare;
    @Value("${registration.password.min}")
    private int minPasswordLength;
    @Value("${registration.password.max}")
    private int maxPasswordLength;

    public boolean isEnabled() {
        return enabled;
    }

    public boolean isPrepare() {
        return prepare;
    }

    @Transactional
    public void check(String username, String email, String password) {
        if (!enabled) {
            throw new InvalidParametersException("Registration disabled", 31);
        }
        if (!USERNAME_PATTERN.matcher(username).matches()) {
            throw new InvalidParametersException("Username contains forbidden characters", 33);
        }
        if (password.isEmpty()) {
            throw new InvalidParametersException("Empty password", 36);
        }
        if (password.length() < minPasswordLength || password.length() > maxPasswordLength) {
            throw new InvalidParametersException(String.format("Password length must be between %d and %d characters", minPasswordLength, maxPasswordLength), 36);
        }
        if (prepareUserService.findByUsername(username).isPresent() || userService.findByUsername(username).isPresent()) {
            throw new InvalidParametersException("Username already registered", 34);
        }
        if (prepareUserService.findByEmail(email).isPresent() || userService.findByEmail(email).isPresent()) {
            throw new InvalidParametersException("Email already registered", 35);
        }
    }

    public RegisterResult register(String username, String email, String password) {
        long id;
        if (prepare) {
            var user = createPrepareUser(username, email, password);
            id = user.getId();
        } else {
            var user = createUser(username, email, password);
            id = user.getId();
        }
        return new RegisterResult(prepare, false, id);
    }

    public User createUser(String username, String email, String password) {
        User user = new User();
        user.setUsername(username);
        user.setUuid(UUID.randomUUID());
        user.setEmail(email);
        passwordCheckService.setPassword(user, password);
        user.setRegistrationDate(LocalDateTime.now());
        user.setGroups(new ArrayList<>());
        userService.save(user);
        return user;
    }

    public PrepareUser createPrepareUser(String username, String email, String password) {
        PrepareUser prepareUser = new PrepareUser();
        prepareUser.setUsername(username);
        prepareUser.setEmail(email);
        passwordCheckService.setPassword(prepareUser, password);
        prepareUser.setDate(LocalDateTime.now());
        return prepareUser;
    }

    public record RegisterResult(boolean prepared, boolean needConfirm, long id) {

    }
}
