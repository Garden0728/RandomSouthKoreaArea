package Service.RandomArea.Config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class KakaoConfig {
    @Value("${kakao.rest.REST_API_KEY}")
    private String KakaoApikey;
}
