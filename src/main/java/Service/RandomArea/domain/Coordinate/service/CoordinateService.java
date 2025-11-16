package Service.RandomArea.domain.Coordinate.service;

import Service.RandomArea.domain.Coordinate.Coordinate;
import lombok.RequiredArgsConstructor;

public interface CoordinateService {
    Coordinate getRandomCoordinate() throws Exception;
}
