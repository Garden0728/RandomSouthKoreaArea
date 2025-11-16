package Service.RandomArea.domain.Polygon;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Geometry;


import java.util.ArrayList;
import java.util.List;
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class KoreaPolygon {
    private List<Geometry> polygons;
    public KoreaPolygon(List<Geometry> polygons) {
        this.polygons = polygons;
    }

}
