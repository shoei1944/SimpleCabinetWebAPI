package pro.gravit.simplecabinet.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pro.gravit.simplecabinet.web.model.User;
import pro.gravit.simplecabinet.web.service.UserService;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    public UserService userService;
    public static record RegisterResponse(long id) {
    }
    public static record RegisterRequest(String username, String email, String password) {
    }
    @PostMapping("/register")
    public RegisterResponse register(@RequestBody RegisterRequest request) {
        User user = userService.register(request.username, request.email, request.password);
        return new RegisterResponse(user.getId());
    }
}
