package Service.RandomArea.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    private final UploadConfig uploadConfig;

    public WebConfig(UploadConfig uploadConfig) {
        this.uploadConfig = uploadConfig;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String location = "file:" + normalizePath(uploadConfig.getUploadDir()) + "/";
        registry.addResourceHandler("/uploads/**")
            .addResourceLocations(location);
    }

    private String normalizePath(String path) {
        return path.replace("\\", "/");
    }
}
