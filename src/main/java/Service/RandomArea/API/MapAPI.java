package Service.RandomArea.API;

import Service.RandomArea.Controller.Dto.APIAddressResponseDto;

public interface MapAPI {
     APIAddressResponseDto getAddress(String x, String y);
     APIAddressResponseDto getRegion(String x, String y);
}
