package lt.ca.javau11.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Component
public class ApiKeyReader {
    @Value("${api.key.path}")
    private String apiKeyPath;

    public String getApiKey() {
        try {
            return Files.readString(Paths.get(apiKeyPath)).trim();
        } catch (IOException e) {
            throw new RuntimeException("Could not read API key from file", e);
        }
    }
}