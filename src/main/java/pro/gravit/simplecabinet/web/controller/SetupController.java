package pro.gravit.simplecabinet.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pro.gravit.simplecabinet.web.configuration.jwt.JwtProvider;
import pro.gravit.simplecabinet.web.exception.InvalidParametersException;
import pro.gravit.simplecabinet.web.model.User;
import pro.gravit.simplecabinet.web.model.UserGroup;
import pro.gravit.simplecabinet.web.service.*;
import pro.gravit.simplecabinet.web.utils.SecurityUtils;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;

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
    public RegisterService registerService;
    @Autowired
    private PasswordCheckService passwordCheckService;

    @GetMapping("/setup")
    public SetupResponse setup() {
        if (userService.findById(1L).isPresent()) {
            throw new InvalidParametersException("Setup is completed", 19);
        }
        var password = SecurityUtils.generateRandomString(32);
        User user = registerService.createUser("admin", "admin@example.com", password);
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

    @GetMapping("/myip")
    public String myIP(HttpServletRequest request) {
        return request.getRemoteAddr();
    }

    public static record SetupResponse(String username, String password, String accessToken) {

    }
}
