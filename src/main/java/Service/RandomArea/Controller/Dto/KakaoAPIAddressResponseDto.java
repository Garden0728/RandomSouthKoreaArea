package Service.RandomArea.Controller.Dto;

import Service.RandomArea.exception.ErrorCode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.List;
public record KakaoAPIAddressResponseDto(
        @Schema(description = "메타정보")
        meta meta,
         @Schema(description = "주소 문서 리스트")
        List<documents> documents
){
    public static record meta(
            int total_count
    ){}
    public static record documents(
            @Schema(description = "도로명 주소")
            road_address road_address,

            @Schema(description = "지번 주소")
            address address
    ){
        public static record road_address(
                String address_name,
                String region_1depth_name,
                String region_2depth_nam,
                String region_3depth_name,
                String road_name,
                String underground_yn,
                String main_building_no,
                String sub_building_no,
                String building_name,
                String zone_no

        ){}
        public static record address(
                String address_name,
                String region_1depth_name,
                String region_2depth_name,
                String region_3depth_name,
                String mountain_yn,
                String main_address_no,
                String sub_address_no
        ){}
    }

}
