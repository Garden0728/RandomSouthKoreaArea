package Service.RandomArea.domain.Polygon;

import Service.RandomArea.Config.Polygon.PolygonConfig;
import Service.RandomArea.exception.CustomException;
import Service.RandomArea.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
@Primary
@RequiredArgsConstructor
public class PolygonServiceImpl implements PolygonService {
    private final PolygonConfig polygonConfig;
    private final GeometryFactory geometryFactory;
    @Override
    public List<List<Double>> getPolygon(double x, double y) throws Exception {
        List<List<Double>> polygon = new ArrayList<>();
        for(Geometry geometry : polygonConfig.getPolygon().getPolygons()){
              if(geometry.contains(geometryFactory.createPoint(new Coordinate(x,y)))){
                  for(Coordinate coordinate : geometry.getCoordinates()){
                      polygon.add(List.of(coordinate.x,coordinate.y));
                  }
                  return polygon;
              }
        }
        throw new CustomException(ErrorCode.NOT_FOUND_POLYGON);

    }
}
