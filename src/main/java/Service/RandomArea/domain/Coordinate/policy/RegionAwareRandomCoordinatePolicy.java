package Service.RandomArea.domain.Coordinate.policy;

import Service.RandomArea.domain.Coordinate.Coordinate;

import java.util.List;

public interface RegionAwareRandomCoordinatePolicy extends RandomCoordinatePolicy {
    Coordinate generate(List<String> regions) throws Exception;
    @Override
    default Coordinate generate() throws Exception {
        return generate(null);
    }
}
