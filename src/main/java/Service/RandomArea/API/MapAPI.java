package Service.RandomArea.API;

import Service.RandomArea.Controller.Dto.APIAddressResponseDto;

public interface MapAPI {
    public APIAddressResponseDto getApiAddress(String x, String y);
}
