package Service.RandomArea.domain.Polygon.service;

import org.locationtech.jts.geom.Geometry;

import java.util.List;

public interface PolygonService {
    List<List<Double>> getPolygon(double x, double y) throws Exception;
    List<Geometry> getPolygonsByRegions(List<String> regions) throws Exception;
    List<Geometry> getPolygonsByRegion(String region) throws Exception;
}
