package Service.RandomArea.client;

import Service.RandomArea.Config.KakaoConfig;
import Service.RandomArea.Controller.Dto.KakaoAPIAddressResponseDto;
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
public class KakaoApiClient {
    private final RestTemplate restTemplate;
    private final KakaoConfig kakaoConfig;
    public KakaoAPIAddressResponseDto getKakaoApiAddress(String x,String y) {
        String url = "https://dapi.kakao.com/v2/local/geo/coord2address.json?x="+x+"&y="+y+"&input_coord=WGS84";
        HttpHeaders headers = new HttpHeaders(); //헤더 객체 생성
        headers.set("Authorization","KakaoAK "+ kakaoConfig.getKakaoApikey());
        HttpEntity<Void> entity = new HttpEntity<>(headers);
        ResponseEntity<KakaoAPIAddressResponseDto> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                KakaoAPIAddressResponseDto.class
        );
        if(response.getBody() != null && response.getBody().meta().total_count() > 0) {
            return response.getBody();
        }
        else{
            throw new CustomException(ErrorCode.NOT_FOUND);
        }
    }

}
