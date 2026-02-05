package Service.RandomArea.Config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class UploadConfig {
    @Value("${uploads.dir:./uploads}")
    private String uploadDir;

    @Value("${uploads.base-url:}")
    private String baseUrl;
}
