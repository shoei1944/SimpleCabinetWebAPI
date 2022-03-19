package pro.gravit.simplecabinet.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pro.gravit.simplecabinet.web.configuration.jwt.JwtProvider;
import pro.gravit.simplecabinet.web.exception.InvalidParametersException;
import pro.gravit.simplecabinet.web.model.User;
import pro.gravit.simplecabinet.web.model.UserGroup;
import pro.gravit.simplecabinet.web.service.PasswordCheckService;
import pro.gravit.simplecabinet.web.service.SessionService;
import pro.gravit.simplecabinet.web.service.UserGroupService;
import pro.gravit.simplecabinet.web.service.UserService;
import pro.gravit.simplecabinet.web.utils.SecurityUtils;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Random;

@RestController
public class SetupController {
    @Autowired
    public UserService userService;
    @Autowired
    public UserGroupService userGroupService;
    @Autowired
    public SessionService sessionService;
    @Autowired
    public JwtProvider jwtProvider;
    @Autowired
    private PasswordCheckService passwordCheckService;

    @GetMapping("/setup")
    public SetupResponse setup() {
        if (userService.findById(1L).isPresent()) {
            throw new InvalidParametersException("Setup is completed", 19);
        }
        var password = SecurityUtils.generateRandomString(32);
        User user = userService.register("admin", "admin@example.com", password);
        UserGroup admin = new UserGroup();
        admin.setUser(user);
        admin.setGroupName("ADMIN");
        admin.setStartDate(LocalDateTime.now());
        userGroupService.save(admin);
        user.setGroups(new ArrayList<>());
        user.getGroups().add(admin);
        userService.save(user);
        var session = sessionService.create(user, "Setup Session");
        var token = jwtProvider.generateNoExpiredJWTToken(session);
        return new SetupResponse(user.getUsername(), password, token.token());
    }

    @GetMapping("/preftest")
    public void test() {
        Random random = new Random();
        for (int i = 0; i < 100000; ++i) {
            String username = "v".concat(Integer.toString(i));
            User user = userService.register(username, username.concat("@example.com"), username);
        }
    }

    @GetMapping("/myip")
    public String myIP(HttpServletRequest request) {
        return request.getRemoteAddr();
    }

    public static record SetupResponse(String username, String password, String accessToken) {

    }
}
