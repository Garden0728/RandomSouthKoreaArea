package Service.RandomArea.Controller.Dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record APIAddressResponseDto(
        @Schema(description = "메타정보")
        meta meta,
        @Schema(description = "주소 문서 리스트")
        List<documents> documents

) {
    public record meta(
            int total_count
    ) {
    }

    public record documents(
            @Schema(description = "지번 주소")
            address address,
            @Schema(description = "행정 법동으로 가져온 지역 명칭 ")
            String address_name
    ) {
        public String getFullAddress() {
            if (this.address != null) {
                return String.join(" ",
                        address.region_1depth_name(),
                        address.region_2depth_name(),
                        address.region_3depth_name());
            }
            return this.address_name;
        }
    }

    public record address(
            String address_name,
            String region_1depth_name,
            String region_2depth_name,
            String region_3depth_name

    ) {
    }
}
