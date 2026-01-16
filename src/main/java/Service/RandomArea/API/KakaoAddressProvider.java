package Service.RandomArea.API;

import Service.RandomArea.Config.API.KakaoConfig;
import Service.RandomArea.Controller.Dto.APIAddressResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@RequiredArgsConstructor
@Component
public class KakaoAddressProvider implements MapAPI {
    private final RestTemplate restTemplate;
    private final KakaoConfig kakaoConfig;
    public APIAddressResponseDto getAddress(String x, String y) {
        String KakaoAddressURl = kakaoConfig.getAddressUrl();
        return SendRequest(x, y, KakaoAddressURl);
    }

    public APIAddressResponseDto getRegion(String x, String y) {
        String KakaoRegionURl = kakaoConfig.getRegionUrl();
        return SendRequest(x, y, KakaoRegionURl);
    }

    private APIAddressResponseDto SendRequest(String x, String y, String ApiURL) {
        String apiUrl = UriComponentsBuilder.fromUriString(ApiURL)
                .queryParam("x", x)
                .queryParam("y", y)
                .queryParam("input_coord", "WGS84")
                .build().toUriString();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "KakaoAK " + kakaoConfig.getKakaoApikey());
        return restTemplate.exchange(apiUrl, HttpMethod.GET, new HttpEntity<>(headers), APIAddressResponseDto.class).getBody();
    }
}
