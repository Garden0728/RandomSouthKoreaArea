package Service.RandomArea.domain.service;

import Service.RandomArea.Controller.Dto.KakaoAPIAddressResponseDto;
import Service.RandomArea.client.KakaoApiClient;
import Service.RandomArea.domain.Address;
import Service.RandomArea.exception.CustomException;
import Service.RandomArea.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static Service.RandomArea.domain.Address.CreateAddress;

@RequiredArgsConstructor
@Service
public class AddressServiceImpl implements AddressService {
    private final KakaoApiClient kakaoApiClient;

    public Address getAddress() {
        return checkCoodinate();
    }

    private String changeAddressName(KakaoAPIAddressResponseDto addressResponseDto) {
        return addressResponseDto.documents().get(0).address().region_1depth_name() + " "
                    + addressResponseDto.documents().get(0).address().region_2depth_name() + " "
                    + addressResponseDto.documents().get(0).address().region_3depth_name();
    }
    private Address checkCoodinate() {
        Address address = null;
        int receive_address = 0;
        while (receive_address ==  0) {
            address = CreateAddress();
            try {
                KakaoAPIAddressResponseDto responseDto = kakaoApiClient.getKakaoApiAddress(address.getX(), address.getY());
                if (responseDto != null && responseDto.meta().total_count() > 0) {
                    receive_address = responseDto.meta().total_count();
                    String Address_name = changeAddressName(responseDto);
                    address.SetAddress_name(Address_name);
                }

            } catch (CustomException e) {
                    e.printStackTrace();
            }
        }
        return address;

    }

}
