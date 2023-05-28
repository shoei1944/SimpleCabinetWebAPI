package pro.gravit.simplecabinet.web.service.storage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pro.gravit.simplecabinet.web.configuration.properties.FileStorageConfig;

import javax.annotation.Priority;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

@Service
@Priority(value = 0)
public class FileStorageService implements StorageService {

    @Autowired
    private FileStorageConfig config;

    @Override
    public URL put(String name, byte[] bytes) throws StorageException {
        try (OutputStream output = new BufferedOutputStream(new FileOutputStream(Path.of(config.getLocalPath()).resolve(name).toFile()))) {
            try (ByteArrayInputStream input = new ByteArrayInputStream(bytes)) {
                input.transferTo(output);
            }
            return getUrl(name);
        } catch (Exception e) {
            throw new StorageException(e);
        }
    }

    @Override
    public URL put(String name, InputStream input, long length) throws StorageException {
        try (OutputStream output = new BufferedOutputStream(new FileOutputStream(Path.of(config.getLocalPath()).resolve(name).toFile()))) {
            input.transferTo(output);
            return getUrl(name);
        } catch (Exception e) {
            throw new StorageException(e);
        }
    }

    @Override
    public URL getUrl(String name) {
        try {
            return new URL(config.getRemoteUrl().concat(URLEncoder.encode(name, StandardCharsets.UTF_8)));
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
}
