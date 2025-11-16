package Service.RandomArea.domain.Coordinate.service;

import Service.RandomArea.API.MapAPI;
import Service.RandomArea.Controller.Dto.APIAddressResponseDto;
import Service.RandomArea.domain.Coordinate.Coordinate;
import Service.RandomArea.domain.Coordinate.policy.RandomCoordinatePolicy;
import Service.RandomArea.domain.address.Address;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
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
        APIAddressResponseDto dto = mapAPI.getApiAddress(coordinate.getX(),coordinate.getY());
        coordinate.SetAddress(CreateAddress(dto.documents().get(0).address().region_1depth_name()+
                " "+dto.documents().get(0).address().region_2depth_name()+
                " "+dto.documents().get(0).address().region_3depth_name()));
        return coordinate;
    }
}
