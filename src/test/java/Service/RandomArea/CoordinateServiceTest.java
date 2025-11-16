package Service.RandomArea;

import Service.RandomArea.API.KakaoApiClient;
import Service.RandomArea.API.MapAPI;
import Service.RandomArea.Controller.Dto.APIAddressResponseDto;
import Service.RandomArea.domain.Coordinate.Coordinate;
import Service.RandomArea.domain.Coordinate.policy.RandomCoordinateGeneratorPolicyV2;
import Service.RandomArea.domain.Coordinate.policy.RandomCoordinatePolicy;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CoordinateServiceTest {
    @Autowired
    private RandomCoordinateGeneratorPolicyV2 policy;
    @Autowired
    private KakaoApiClient kakaoApiClient;
    @Test
    public void getRandomCoordinate() throws Exception {
        Coordinate coordinate = policy.generate();
        System.out.println(coordinate.getX() + " " + coordinate.getY());
        Assertions.assertNotNull(coordinate);
        APIAddressResponseDto apiAddress = kakaoApiClient.getApiAddress(coordinate.getX(), coordinate.getY());
        System.out.println("apiAddress = " + apiAddress.documents().get(0).address().address_name());
    }


}
