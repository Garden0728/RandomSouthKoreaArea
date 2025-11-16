package Service.RandomArea.Controller.Dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;
public record APIAddressResponseDto(
        @Schema(description = "메타정보")
        meta meta,
         @Schema(description = "주소 문서 리스트")
        List<documents> documents

){
    public static record meta(
            int total_count
    ){}
    public static record documents(
            @Schema(description = "지번 주소")
            address address
    ){

        public static record address(
                String address_name,
                String region_1depth_name,
                String region_2depth_name,
                String region_3depth_name

        ){}
    }

}
