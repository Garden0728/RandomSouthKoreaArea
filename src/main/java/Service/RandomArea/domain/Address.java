package Service.RandomArea.domain;

import lombok.Getter;
import org.springframework.ui.Model;

import java.util.Random;

@Getter
public class Address {
    private String x;
    private String y;
    private String address_name;
  //  String zone_no; //도로명 주소
    public static Address CreateAddress() {
        Address address = new Address();
        double x_gap = 132.0-124.5;
        double y_gap = 38.9-33.0;
        Random random = new Random();
        String random_x = Double.toString(124.5+random.nextDouble(x_gap)); ///7.5는 나올 확률이 없다.. 이거 개선을 해야할까?
        String random_y = Double.toString(33.0+random.nextDouble(y_gap));
        /*String random_x = String.format("%.3f",124.5+(x_gap * Math.random())); //gpt가 짜준 알고리즘인데 위에랑 다를게 없어보인다.
        String random_y = String.format("%.3f",33.0+(y_gap * Math.random()));*/
        address.x = random_x;
        address.y = random_y;
        return address;
    }
    public void SetAddress_name(String address_name) {
        this.address_name = address_name;
    }


}
