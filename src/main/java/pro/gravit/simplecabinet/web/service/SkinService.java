package pro.gravit.simplecabinet.web.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pro.gravit.simplecabinet.web.dto.UserDto;
import pro.gravit.simplecabinet.web.model.BasicUser;
import pro.gravit.simplecabinet.web.model.User;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Map;

@Service
public class SkinService {
    private static final SkinLimits DEFAULT_SKIN_LIMITS = new SkinLimits(64, 64, 32 * 1024);
    @Value("${skins.path.skin}")
    private Path skinBasePath;
    @Value("${skins.path.cloak}")
    private Path cloakBasePath;
    @Value("${skins.calculatedigest}")
    private boolean isCalculateDigest;

    @Value("${skins.url.skin}")
    private String skinBaseUrl;
    @Value("${skins.url.cloak}")
    private String cloakBaseUrl;

    public Path getSkinPath(BasicUser user) {
        return skinBasePath.resolve(user.getUsername().concat(".png"));
    }

    public Path getCloakPath(BasicUser user) {
        return cloakBasePath.resolve(user.getUsername().concat(".png"));
    }

    public String getSkinUrl(BasicUser user) {
        return skinBaseUrl.concat(user.getUsername()).concat(".png");
    }

    public String getCloakUrl(BasicUser user) {
        return cloakBaseUrl.concat(user.getUsername()).concat(".png");
    }

    public SkinLimits getSkinLimits(UserService.CurrentUser user) {
        return getSkinLimits("skin", user);
    }

    public SkinLimits getCloakLimits(UserService.CurrentUser user) {
        return getSkinLimits("cloak", user);
    }

    public boolean checkLimitsPre(MultipartFile file, SkinLimits limits) {
        return file.getSize() <= limits.maxBytes();
    }

    public boolean checkLimitsPost(Path file, SkinLimits limits) {
        try {
            BufferedImage image = ImageIO.read(file.toFile());
            if (image.getHeight() > limits.maxHeight()) {
                return false;
            }
            if (image.getWidth() > limits.maxWidth()) {
                return false;
            }
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    private SkinLimits getSkinLimits(String type, UserService.CurrentUser user) {
        var maxHeight = user.getPermission(String.format("upload.%s.height", type));
        var maxWidth = user.getPermission(String.format("upload.%s.width", type));
        var maxBytes = user.getPermission(String.format("upload.%s.bytes", type));
        return new SkinLimits(maxHeight == null ? DEFAULT_SKIN_LIMITS.maxHeight : Integer.parseInt(maxHeight),
                maxWidth == null ? DEFAULT_SKIN_LIMITS.maxWidth : Integer.parseInt(maxWidth),
                maxBytes == null ? DEFAULT_SKIN_LIMITS.maxBytes : Integer.parseInt(maxBytes));
    }

    public UserDto.UserTexture getSkinTexture(User user) {
        var path = getSkinPath(user);
        if (Files.exists(path)) {
            var url = getSkinUrl(user);
            byte[] digest;
            if (isCalculateDigest) {
                digest = skinDigest(path);
            } else {
                digest = new byte[0];
            }
            var model = user.getSkinModel();
            Map<String, String> metadata;
            if (model != null) {
                metadata = Map.of("model", model);
            } else {
                metadata = null;
            }
            return new UserDto.UserTexture(url, Base64.getUrlEncoder().encodeToString(digest), metadata);
        } else {
            return null;
        }
    }

    private byte[] skinDigest(Path path) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(Files.readAllBytes(path));
            return md.digest();
        } catch (NoSuchAlgorithmException | IOException e) {
            throw new SecurityException(e);
        }
    }

    public UserDto.UserTexture getCloakTexture(User user) {
        var path = getCloakPath(user);
        if (Files.exists(path)) {
            var url = getCloakUrl(user);
            byte[] digest;
            if (isCalculateDigest) {
                digest = skinDigest(path);
            } else {
                digest = new byte[0];
            }
            return new UserDto.UserTexture(url, Base64.getUrlEncoder().encodeToString(digest), null);
        } else {
            return null;
        }
    }

    public record SkinLimits(int maxHeight, int maxWidth, long maxBytes) {

    }
}
