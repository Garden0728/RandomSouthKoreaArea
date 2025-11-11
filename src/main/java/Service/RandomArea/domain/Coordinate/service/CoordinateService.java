package Service.RandomArea.domain.Coordinate.service;

import Service.RandomArea.API.MapAPI;
import Service.RandomArea.Controller.Dto.APIAddressResponseDto;
import Service.RandomArea.domain.Coordinate.Coordinate;
import Service.RandomArea.domain.Coordinate.policy.RandomCoordinatePolicy;
import Service.RandomArea.domain.address.Address;
import Service.RandomArea.exception.CustomException;
import Service.RandomArea.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static Service.RandomArea.domain.address.Address.CreateAddress;

@Slf4j
@RequiredArgsConstructor
@Service
public class CoordinateService {
    private final RandomCoordinatePolicy randomCoordinatePolicy;
    private final MapAPI mapAPI;

    public Coordinate getRandomCoordinate() {
        int tryCount = 0;
        int tryMaxTry = 10;
        boolean success = false;
        Coordinate coordinate = null;
        while (tryCount < tryMaxTry && !success) {
            tryCount++;
            coordinate = randomCoordinatePolicy.generate();
            success = validateCoordinate(coordinate, tryCount);
        }
        if (!success) {
                throw new CustomException(ErrorCode.RETRY_MAX_COUNT_EXCEEDED);
        }
        return coordinate;
    }

    private boolean validateCoordinate(Coordinate coordinate, int tryCount) {
        try {
            APIAddressResponseDto apiAddressResponseDto = mapAPI.getApiAddress(coordinate.getX(), coordinate.getY());
            String address_name = extractAddress(apiAddressResponseDto);
            Address address = CreateAddress(address_name);
            coordinate.attachAddress(address);
            return true;
        } catch (CustomException e) {
            log.warn("좌표 검증 실패 ({}번째): {}", tryCount, e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    private String extractAddress(APIAddressResponseDto apiAddressResponseDto) {
        if(apiAddressResponseDto == null) {
            throw new CustomException(ErrorCode.NOT_FOUND);
        }
        var address = apiAddressResponseDto.documents().get(0).address();

        return address.region_1depth_name() + " " + address.region_2depth_name() + " " + address.region_3depth_name();
    }
}
