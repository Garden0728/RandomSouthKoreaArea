package Service.RandomArea.domain.Coordinate.service;

import Service.RandomArea.API.MapAPI;
import Service.RandomArea.Controller.Dto.APIAddressResponseDto;
import Service.RandomArea.domain.Coordinate.Coordinate;
import Service.RandomArea.domain.Coordinate.policy.RandomCoordinatePolicy;
import Service.RandomArea.exception.CustomException;
import Service.RandomArea.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import static Service.RandomArea.domain.address.Address.CreateAddress;

@Slf4j
@RequiredArgsConstructor
@Service
@Primary
public class CoordinateServiceV2 implements CoordinateService {
    private final MapAPI mapAPI;
    private final RandomCoordinatePolicy randomCoordinatePolicy;
    public Coordinate getRandomCoordinate() throws Exception {
        Coordinate coordinate = randomCoordinatePolicy.generate();
        APIAddressResponseDto dto = mapAPI.getAddress(coordinate.getX(),coordinate.getY());
        if(isInvalid(dto)) {
            log.info("지번 주소 확인 불가 행정 구역으로 재시도 : {}, {}",coordinate.getX(),coordinate.getY());
            dto = mapAPI.getRegion(coordinate.getX(),coordinate.getY());
            if(isInvalid(dto)){
                throw new CustomException(ErrorCode.NOT_FOUND);
            }
        }
        coordinate.SetAddress(CreateAddress(dto.documents().get(0).getFullAddress()));
        return coordinate;
    }
    private boolean isInvalid(APIAddressResponseDto dto) {
        return dto == null || dto.meta().total_count() == 0;
    }
}
