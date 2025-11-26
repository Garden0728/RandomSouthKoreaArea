package Service.RandomArea.domain.Coordinate.policy;

import Service.RandomArea.Config.Polygon.PolygonConfig;
import Service.RandomArea.domain.Coordinate.Coordinate;
import Service.RandomArea.domain.Polygon.PolygonService;
import Service.RandomArea.domain.Polygon.PolygonServiceImpl;
import Service.RandomArea.exception.CustomException;
import Service.RandomArea.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;

@Component
@Slf4j
@Primary
@RequiredArgsConstructor
public class RandomCoordinateGeneratorPolicyV2 implements RandomCoordinatePolicy {
/*    private final PolygonConfig polygonConfig;
    private final GeometryFactory geometryFactory = new GeometryFactory();*/
    private final PolygonService polygonService;
    private final Random random = new Random();
    public Coordinate generate() throws Exception {
        while (true) {
            try {
                double x = 124.0 + random.nextDouble() * (132.0 - 124.0);
                double y = 33.0 + random.nextDouble() * (39.0 - 33.0);
                List<List<Double>> polygon = polygonService.getPolygon(x, y);
                if(polygon == null || polygon.isEmpty()){
                    throw new CustomException(ErrorCode.MISS_COORDINATE);
                }
                Coordinate coordinate = new Coordinate(String.valueOf(x), String.valueOf(y));
                coordinate.SetPolygonCoordinates(polygon);
                return coordinate;

            } catch (CustomException e) {
                log.error(e.getMessage());
            }

        }
    }
}
