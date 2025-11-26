package Service.RandomArea.domain.Coordinate;

import Service.RandomArea.domain.address.Address;
import lombok.Getter;

import java.util.List;

@Getter
public class Coordinate {
    private String x;
    private String y;
    private Address address;
    private List<List<Double>> polygonCoordinates;
    public Coordinate(String x, String y) {
        this.x = x;
        this.y = y;
    }
    public void SetAddress(Address address) {
        this.address = address;
    }
    public void SetPolygonCoordinates(List<List<Double>> polygonCoordinates) {
        this.polygonCoordinates = polygonCoordinates;
    }
}
