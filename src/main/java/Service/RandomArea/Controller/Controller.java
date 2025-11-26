package Service.RandomArea.Controller;

import Service.RandomArea.domain.Coordinate.Coordinate;
import Service.RandomArea.domain.Coordinate.service.CoordinateService;
import Service.RandomArea.domain.Coordinate.service.CoordinateServiceV1;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.InputStream;

@RestController
@RequestMapping("/api/v1/")
@RequiredArgsConstructor
public class Controller {

    private final CoordinateService coordinateService;
    @GetMapping("Random_coordinate-Address/create")
    public Coordinate getCoordinate() throws Exception {
        return coordinateService.getRandomCoordinate();
    }
}
