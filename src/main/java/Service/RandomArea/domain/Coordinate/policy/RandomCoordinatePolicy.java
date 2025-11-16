package Service.RandomArea.domain.Coordinate.policy;

import Service.RandomArea.domain.Coordinate.Coordinate;

public interface RandomCoordinatePolicy {
    public Coordinate generate() throws Exception;
}
