package Service.RandomArea.Config.Polygon;

import org.locationtech.jts.geom.GeometryFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GeometryFactoryConfig {
    @Bean
    public GeometryFactory getPolygonConfig() {
        return new GeometryFactory();
    }
}
