package Service.RandomArea.domain.Coordinate.service;

import Service.RandomArea.domain.Coordinate.Coordinate;

import java.util.List;

public interface RegionAwareCoordinateService {
    Coordinate getRandomCoordinate(List<String> regions) throws Exception;
}
