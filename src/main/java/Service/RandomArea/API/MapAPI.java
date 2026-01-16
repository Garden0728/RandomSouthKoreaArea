package Service.RandomArea.API;

import Service.RandomArea.Controller.Dto.APIAddressResponseDto;

public interface MapAPI {
    public APIAddressResponseDto getAddress(String x, String y);
    public APIAddressResponseDto getRegion(String x, String y);
}
