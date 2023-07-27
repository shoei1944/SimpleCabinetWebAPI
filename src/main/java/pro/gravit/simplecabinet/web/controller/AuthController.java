package pro.gravit.simplecabinet.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pro.gravit.simplecabinet.web.configuration.jwt.JwtProvider;
import pro.gravit.simplecabinet.web.dto.UserDto;
import pro.gravit.simplecabinet.web.exception.AuthException;
import pro.gravit.simplecabinet.web.exception.EntityNotFoundException;
import pro.gravit.simplecabinet.web.service.*;
import pro.gravit.simplecabinet.web.utils.SecurityUtils;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserService userService;
    @Autowired
    private BanService banService;
    @Autowired
    private PasswordCheckService passwordCheckService;
    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private SessionService sessionService;
    @Autowired
    private DtoService dtoService;
    @Autowired
    private RegisterService registerService;

    @PostMapping("/register")
    public RegisterResponse register(@RequestBody RegisterRequest request) {
        registerService.check(request.username, request.email, request.password);
        var result = registerService.register(request.username, request.email, request.password);
        return new RegisterResponse(result.id()); // TODO
    }

    @PostMapping("/authorize")
    public ResponseEntity<AuthResponse> auth(@RequestBody AuthRequest request) {
        var optional = userService.findByUsernameOrEmail(request.username);
        if (optional.isEmpty()) {
            throw new AuthException("User not found", 3);
        }
        var user = optional.get();
        var banInfo = banService.findBanByUser(user);
        if (banInfo.isPresent()) {
            var info = banInfo.get();
            throw new AuthException(String.format("You banned: %s expired %s", info.getReason(), info.getEndAt() == null ? "never" : info.getEndAt().toString()), 4);
        }
        var success = passwordCheckService.checkPassword(user, request.password);
        if (!success) {
            throw new AuthException("Password not correct", 5);
        }
        if (user.getTotpSecretKey() != null) {
            if (request.totpPassword == null) {
                throw new AuthException("auth.require2fa", 7);
            }
            if (!passwordCheckService.checkTotpPassword(user, request.totpPassword)) {
                throw new AuthException("2FA Password not correct", 6);
            }
        }
        var session = sessionService.create(user, "Basic");
        var token = jwtProvider.generateToken(session);
        HttpCookie cookie = ResponseCookie.from("session", token.token())
                .path("/")
                .sameSite("Strict")
                .build();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Set-Cookie", cookie.toString());
        return new ResponseEntity<>(new AuthResponse(token.token(), session.getRefreshToken(), token.getExpire()), headers, HttpStatus.OK);
    }

    @PostMapping("/refresh")
    public AuthResponse refreshToken(@RequestBody RefreshTokenRequest request) {
        var sessionOptional = sessionService.updateRefreshToken(request.refreshToken);
        if (sessionOptional.isEmpty()) {
            throw new AuthException("Invalid refreshToken", 8);
        }
        var session = sessionOptional.get();
        var token = jwtProvider.generateToken(session);
        return new AuthResponse(token.token(), session.getRefreshToken(), token.getExpire());
    }

    @PostMapping("/logout")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> logout() {
        var details = SecurityUtils.getUser();
        if (!sessionService.deactivateById(details.getSessionId())) {
            throw new AuthException("Invalid session", 9);
        }
        HttpCookie cookie = ResponseCookie.from("session", "deleted")
                .path("/")
                .sameSite("Strict")
                .maxAge(0)
                .build();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Set-Cookie", cookie.toString());
        return ResponseEntity.ok().headers(headers).build();
    }

    @GetMapping("/userinfo")
    @PreAuthorize("isAuthenticated()")
    public UserDto getUserInfo() {
        var details = SecurityUtils.getUser();
        var userOptional = userService.findByIdFetchAssets(details.getUserId());
        if (userOptional.isEmpty()) {
            throw new EntityNotFoundException("User not found");
        }
        return dtoService.toPrivateUserDto(userOptional.get());
    }

    public static record RegisterResponse(long id) {
    }

    public static record RegisterRequest(String username, String email, String password) {
    }

    public static record AuthRequest(String username, String password, String totpPassword) {
    }

    public static record AuthResponse(String accessToken, String refreshToken, long expire) {
    }

    public static record RefreshTokenRequest(String refreshToken) {

    }
}
