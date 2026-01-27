package Service.RandomArea.domain.Coordinate.policy;

import Service.RandomArea.domain.Polygon.service.PolygonService;
import org.junit.jupiter.api.Test;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Polygon;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;

class RandomCoordinateGeneratorPolicyDistributionTest {
    @Test
    void regionSelectionIsRoughlyUniform() throws Exception {
        GeometryFactory geometryFactory = new GeometryFactory();
        Polygon square = geometryFactory.createPolygon(new Coordinate[]{
                new Coordinate(0, 0),
                new Coordinate(1, 0),
                new Coordinate(1, 1),
                new Coordinate(0, 1),
                new Coordinate(0, 0)
        });

        FakePolygonService polygonService = new FakePolygonService(List.of(square));
        RandomCoordinateGeneratorPolicy policy = new RandomCoordinateGeneratorPolicy(polygonService, geometryFactory);

        List<String> regions = List.of("seoul", "gyeonggi", "jeju");
        int samples = 6000;
        for (int i = 0; i < samples; i++) {
            policy.generate(regions);
        }

        Map<String, Integer> counts = polygonService.getCounts();
        double expected = samples / (double) regions.size();
        double tolerance = expected * 0.12; // 12% 허용 오차

        for (String region : regions) {
            int count = counts.getOrDefault(region, 0);
            assertTrue(Math.abs(count - expected) <= tolerance,
                    "Region " + region + " count=" + count + " expected=" + expected);
        }
    }

    private static class FakePolygonService implements PolygonService {
        private final List<Geometry> polygons;
        private final Map<String, Integer> counts = new HashMap<>();

        private FakePolygonService(List<Geometry> polygons) {
            this.polygons = polygons;
        }

        @Override
        public List<List<Double>> getPolygon(double x, double y) {
            throw new UnsupportedOperationException("Not used in this test");
        }

        @Override
        public List<Geometry> getPolygonsByRegions(List<String> regions) {
            return polygons;
        }

        @Override
        public List<Geometry> getPolygonsByRegion(String region) {
            counts.merge(region, 1, Integer::sum);
            return polygons;
        }

        public Map<String, Integer> getCounts() {
            return counts;
        }
    }
}
