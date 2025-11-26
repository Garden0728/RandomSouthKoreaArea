package Service.RandomArea.domain.Polygon;

import Service.RandomArea.domain.Coordinate.Coordinate;
import org.locationtech.jts.geom.Polygon;

import java.util.List;

public interface PolygonService {
    public List<List<Double>> getPolygon(double x, double y) throws Exception;
}
