package Service.RandomArea.domain.Polygon;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Geometry;


import java.util.List;
import java.util.Map;
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class KoreaPolygon {
    private List<Geometry> polygons;
    private Map<String, List<Geometry>> regionIndex;

    public KoreaPolygon(List<Geometry> polygons, Map<String, List<Geometry>> regionIndex) {
        this.polygons = polygons;
        this.regionIndex = regionIndex;
    }

    public List<Geometry> getPolygonsByRegions(List<String> regions) {
        if (regions == null || regions.isEmpty() || regionIndex == null) {
            return polygons;
        }
        List<Geometry> result = new java.util.ArrayList<>();
        for (String region : regions) {
            List<Geometry> list = regionIndex.get(region);
            if (list != null) {
                result.addAll(list);
            }
        }
        return result;
    }
}
