package pro.gravit.simplecabinet.web.utils;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import pro.gravit.simplecabinet.web.exception.AuthException;
import pro.gravit.simplecabinet.web.service.UserDetailsService;

import java.security.SecureRandom;
import java.util.Random;

public class SecurityUtils {
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0987654321";

    public static UserDetailsService.CabinetUserDetails tryUser() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            return null;
        }
        return (UserDetailsService.CabinetUserDetails) auth.getPrincipal();
    }

    public static UserDetailsService.CabinetUserDetails getUser() {
        var user = tryUser();
        if (user == null) {
            throw new AuthException("Session not found");
        }
        return user;
    }

    public static String generateRandomString(int length) {
        Random rng = new SecureRandom();
        char[] text = new char[length];
        for (int i = 0; i < length; i++) {
            text[i] = CHARACTERS.charAt(rng.nextInt(CHARACTERS.length()));
        }
        return new String(text);
    }

    public static boolean verifyAuthMeSha256Password(String password, String hashedPassword) {
        String[] splited = hashedPassword.split("\\$");
        if (splited.length != 4) {
            return false;
        }
        String salt = splited[2];
        String saltedHash = splited[3];
        String checkHash = DigestUtils.sha256Hex(DigestUtils.sha256Hex(password).concat(salt));
        return saltedHash.equals(checkHash);
    }
}
