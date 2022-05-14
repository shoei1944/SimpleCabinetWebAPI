package pro.gravit.simplecabinet.web;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class WebApplicationOnStartup {
    public static void prepare() throws IOException {
        Path path = Paths.get("application.properties");
        if (Files.notExists(path)) {
            URL url = WebApplicationOnStartup.class.getResource("/application.properties");
            if (url == null) {
                throw new RuntimeException("Resource 'application.properties' not found");
            }
            URLConnection c = url.openConnection();
            try (InputStream input = c.getInputStream()) {
                try (OutputStream output = new FileOutputStream(path.toFile())) {
                    input.transferTo(output);
                }
            }
            {
                Path dir = Paths.get("assets");
                if (Files.notExists(dir)) {
                    Files.createDirectories(dir);
                }
            }

            System.out.println("File 'application.properties' created. Stop...");
            System.exit(0);
        }
    }
}
