package Service.RandomArea.Controller;

import Service.RandomArea.Controller.Dto.KakaoAPIAddressResponseDto;
import Service.RandomArea.domain.Address;
import Service.RandomArea.Config.KakaoConfig;
import Service.RandomArea.domain.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/v1/Kakao")
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;
    @GetMapping("/coordinate-change-address")
    public Address coordinateChangeAddress() {
        Address address = addressService.getAddress();
        return address;
    }
}
