package Service.RandomArea.Config.Polygon;

import Service.RandomArea.domain.Polygon.KoreaPolygon;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.MultiPolygon;
import org.locationtech.jts.io.geojson.GeoJsonReader;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class PolygonIndexBuilder {
    private final ObjectMapper mapper;
    private final GeoJsonReader reader = new GeoJsonReader();

    public KoreaPolygon build(String json) throws Exception {
        JsonNode jsonNode = mapper.readTree(json);
        List<Geometry> geometries = new ArrayList<>();
        Map<String, List<Geometry>> regionIndex = new HashMap<>();

        for (JsonNode feature : jsonNode.get("features")) {
            Geometry geometry = reader.read(mapper.writeValueAsString(feature.get("geometry")));
            String region = null;
            JsonNode properties = feature.get("properties");
            if (properties != null && properties.has("region")) {
                region = properties.get("region").asText();
            }

            if (geometry instanceof MultiPolygon) {
                Geometry largest = null;
                double maxArea = -1.0;
                for (int i = 0; i < geometry.getNumGeometries(); i++) {
                    Geometry g = geometry.getGeometryN(i).buffer(0);
                    double area = g.getArea();
                    if (area > maxArea) {
                        maxArea = area;
                        largest = g;
                    }
                }
                if (largest != null) {
                    geometries.add(largest);
                    if (region != null && !region.isBlank()) {
                        regionIndex.computeIfAbsent(region, key -> new ArrayList<>()).add(largest);
                    }
                }
            } else {
                Geometry g = geometry.buffer(0);
                geometries.add(g);
                if (region != null && !region.isBlank()) {
                    regionIndex.computeIfAbsent(region, key -> new ArrayList<>()).add(g);
                }
            }
        }

        return new KoreaPolygon(geometries, regionIndex);
    }
}
