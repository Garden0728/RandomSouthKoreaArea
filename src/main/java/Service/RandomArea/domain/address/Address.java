package Service.RandomArea.domain.address;

import Service.RandomArea.domain.Coordinate.Coordinate;
import lombok.Getter;

@Getter
public class Address {
    private String address_name;
  //  String zone_no; //도로명 주소
    public static Address CreateAddress(String address_name) {
        Address address = new Address();
        address.address_name = address_name;
        return address;
    }

}
