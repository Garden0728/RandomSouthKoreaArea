package Service.RandomArea.Controller.Dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record RegionOptionDto(
        @Schema(description = "선택한 지역 키 목록")
        List<String> regions
) {
}
