package Service.RandomArea.Config.API;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class SlackConfig {
    @Value("${slack.webhook.url:}")
    private String webhookUrl;
}
