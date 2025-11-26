package Service.RandomArea.Config.Polygon;

import Service.RandomArea.Config.Json.JsonReader;
import Service.RandomArea.domain.Polygon.KoreaPolygon;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.MultiPolygon;
import org.locationtech.jts.io.geojson.GeoJsonReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Getter
@Configuration
@RequiredArgsConstructor
public class PolygonConfigImplV1 implements PolygonConfig {
    private final JsonReader jsonInputConfig;
    private final ObjectMapper mapper; //ObjectMapper도 자동으로 스피링 빈에 등록이 된다.
    private final GeoJsonReader reader = new GeoJsonReader();
    @Bean
    public KoreaPolygon getPolygon() throws Exception {
            List<Geometry> geometries = getPolygon(jsonInputConfig.getJsonString("Poly.json"));
            return new KoreaPolygon(geometries);
    }
    private List<Geometry> getPolygon(String json) throws Exception {
        JsonNode jsonNode = mapper.readTree(json);
        List<Geometry> geometries = new ArrayList<>();
        for(JsonNode feature : jsonNode.get("features")) {
            Geometry geometry = reader.read(mapper.writeValueAsString(feature.get("geometry")));
            if(geometry instanceof MultiPolygon) {
                for(int i =0; i < geometry.getNumGeometries(); i++) {
                    geometries.add(geometry.getGeometryN(i).buffer(0));
                }
            }
            else{
                geometries.add(geometry.buffer(0));
            }
        }
        return geometries;


    }

}
