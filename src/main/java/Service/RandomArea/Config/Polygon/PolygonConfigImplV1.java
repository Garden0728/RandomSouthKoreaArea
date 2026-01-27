package Service.RandomArea.Config.Polygon;

import Service.RandomArea.Config.Json.JsonReader;
import Service.RandomArea.domain.Polygon.KoreaPolygon;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
@RequiredArgsConstructor
public class PolygonConfigImplV1 implements PolygonConfig {
    private final JsonReader jsonInputConfig;
    private final PolygonIndexBuilder polygonIndexBuilder;

    @Bean
    public KoreaPolygon getPolygon() throws Exception {
        String json = jsonInputConfig.getJsonString("Poly.region.json");
        return polygonIndexBuilder.build(json);
    }
}
