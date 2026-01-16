package Service.RandomArea;

import Service.RandomArea.API.KakaoAddressProvider;
import Service.RandomArea.Controller.Dto.APIAddressResponseDto;
import Service.RandomArea.domain.Coordinate.Coordinate;
import Service.RandomArea.domain.Coordinate.policy.RandomCoordinateGeneratorPolicyV2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CoordinateServiceTest {
    @Autowired
    private RandomCoordinateGeneratorPolicyV2 policy;
    @Autowired
    private KakaoAddressProvider kakaoApiClient;
    @Test
    public void getRandomCoordinate() throws Exception {
        Coordinate coordinate = policy.generate();
        System.out.println(coordinate.getX() + " " + coordinate.getY());
        Assertions.assertNotNull(coordinate);
        APIAddressResponseDto apiAddress = kakaoApiClient.getAddress(coordinate.getX(), coordinate.getY());
        System.out.println(apiAddress.documents().get(0).getFullAddress());
        APIAddressResponseDto apiAddress2 = kakaoApiClient.getRegion(coordinate.getX(), coordinate.getY());
        System.out.println(apiAddress2.documents().get(0).getFullAddress());
    }



}
