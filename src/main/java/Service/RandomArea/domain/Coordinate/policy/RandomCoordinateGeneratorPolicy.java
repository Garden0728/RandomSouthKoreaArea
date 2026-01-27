package Service.RandomArea.domain.Coordinate.policy;

import Service.RandomArea.domain.Coordinate.Coordinate;
import Service.RandomArea.domain.Polygon.service.PolygonService;
import Service.RandomArea.exception.CustomException;
import Service.RandomArea.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.locationtech.jts.geom.Envelope;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
@Slf4j
@RequiredArgsConstructor
public class RandomCoordinateGeneratorPolicy implements RegionAwareRandomCoordinatePolicy {
    private final PolygonService polygonService;
    private final GeometryFactory geometryFactory;
    private final Random random = new Random();

    @Override
    public Coordinate generate(List<String> regions) throws Exception {
        List<Geometry> polygons;
        if (regions == null || regions.isEmpty()) {
            polygons = polygonService.getPolygonsByRegions(null);
        } else {
            String selectedRegion = regions.get(random.nextInt(regions.size()));
            polygons = polygonService.getPolygonsByRegion(selectedRegion);
        }
        if (polygons == null || polygons.isEmpty()) {
            throw new CustomException(ErrorCode.NOT_FOUND_POLYGON);
        }

        int tryCount = 0;
        int maxTry = 1000;
        while (tryCount < maxTry) {
            tryCount++;
            Geometry geometry = polygons.get(random.nextInt(polygons.size()));
            Envelope env = geometry.getEnvelopeInternal(); // geometry의 바운딩 박스(최소/최대 범위)
            double x = env.getMinX() + random.nextDouble() * (env.getMaxX() - env.getMinX());
            double y = env.getMinY() + random.nextDouble() * (env.getMaxY() - env.getMinY());
            Point point = geometryFactory.createPoint(new org.locationtech.jts.geom.Coordinate(x, y));
            if (!geometry.contains(point)) {
                continue;
            }
            Coordinate coordinate = new Coordinate(String.valueOf(x), String.valueOf(y));
            coordinate.SetPolygonCoordinates(toPolygonList(geometry));
            return coordinate;
        }
        throw new CustomException(ErrorCode.RETRY_MAX_COUNT_EXCEEDED);
    }

    private List<List<Double>> toPolygonList(Geometry geometry) {
        List<List<Double>> polygon = new ArrayList<>();
        for (org.locationtech.jts.geom.Coordinate coordinate : geometry.getCoordinates()) {
            polygon.add(List.of(coordinate.x, coordinate.y));
        }
        return polygon;
    }
}
