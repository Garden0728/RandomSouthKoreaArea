package Service.RandomArea.API;

import Service.RandomArea.Config.API.KakaoConfig;
import Service.RandomArea.Controller.Dto.APIAddressResponseDto;
import Service.RandomArea.exception.CustomException;
import Service.RandomArea.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
@Slf4j
@RequiredArgsConstructor
@Component
public class KakaoApiClient implements MapAPI {
    private final RestTemplate restTemplate;
    private final KakaoConfig kakaoConfig;
    public APIAddressResponseDto getApiAddress(String x, String y) {
        String url = "https://dapi.kakao.com/v2/local/geo/coord2address.json?x="+x+"&y="+y+"&input_coord=WGS84";
        HttpHeaders headers = new HttpHeaders(); //헤더 객체 생성
        headers.set("Authorization","KakaoAK "+ kakaoConfig.getKakaoApikey());
        HttpEntity<Void> entity = new HttpEntity<>(headers);
        ResponseEntity<APIAddressResponseDto> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                APIAddressResponseDto.class
        );
        if(response.getBody() != null && response.getBody().meta().total_count() > 0) {
            return response.getBody();
        }
        else{
            throw new CustomException(ErrorCode.NOT_FOUND);
        }
    }

}
