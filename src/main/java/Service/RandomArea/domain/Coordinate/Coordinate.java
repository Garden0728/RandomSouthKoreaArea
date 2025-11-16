package Service.RandomArea.domain.Coordinate;

import Service.RandomArea.domain.address.Address;
import lombok.Getter;

@Getter
public class Coordinate {
    private String x;
    private String y;
    private Address address;
    public Coordinate(String x, String y) {
        this.x = x;
        this.y = y;
    }
    public void SetAddress(Address address) {
        this.address = address;
    }
}
