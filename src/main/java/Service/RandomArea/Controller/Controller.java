package Service.RandomArea.Controller;

import Service.RandomArea.Controller.Dto.RegionOptionDto;
import Service.RandomArea.domain.Coordinate.Coordinate;
import Service.RandomArea.domain.Coordinate.service.RegionAwareCoordinateService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;

@RestController
@RequestMapping("/api/v1/")
@RequiredArgsConstructor
public class Controller {

    @Qualifier("coordinateServiceV3")
    private final RegionAwareCoordinateService coordinateService;

    @GetMapping("Random_coordinate-Address/create")
    public Coordinate getCoordinate(@RequestParam(name = "region", required = false) List<String> regions) throws Exception {
        RegionOptionDto dto = new RegionOptionDto(regions);
        return coordinateService.getRandomCoordinate(dto.regions());
    }
}
