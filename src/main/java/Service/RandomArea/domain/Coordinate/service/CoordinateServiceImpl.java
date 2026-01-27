package Service.RandomArea.domain.Coordinate.service;

import Service.RandomArea.API.MapAPI;
import Service.RandomArea.Controller.Dto.APIAddressResponseDto;
import Service.RandomArea.domain.Coordinate.Coordinate;
import Service.RandomArea.domain.Coordinate.policy.RegionAwareRandomCoordinatePolicy;
import Service.RandomArea.exception.CustomException;
import Service.RandomArea.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static Service.RandomArea.domain.address.Address.CreateAddress;

@Slf4j
@RequiredArgsConstructor
@Service("coordinateServiceV3")
public class CoordinateServiceImpl implements CoordinateService, RegionAwareCoordinateService {
    private final MapAPI mapAPI;
    private final RegionAwareRandomCoordinatePolicy randomCoordinatePolicy;

    @Override
    public Coordinate getRandomCoordinate() throws Exception {
        return getRandomCoordinate(null);
    }

    @Override
    public Coordinate getRandomCoordinate(List<String> regions) throws Exception {
        Coordinate coordinate = randomCoordinatePolicy.generate(regions);
        APIAddressResponseDto dto = mapAPI.getAddress(coordinate.getX(), coordinate.getY());
        if (isInvalid(dto)) {
            log.info("지번 주소 확인 불가로 도로명 주소로 재시도: {}, {}", coordinate.getX(), coordinate.getY());
            dto = mapAPI.getRegion(coordinate.getX(), coordinate.getY());
            if (isInvalid(dto)) {
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
