package Service.RandomArea.domain.Coordinate.policy;

import Service.RandomArea.domain.Coordinate.Coordinate;
import org.springframework.stereotype.Component;

import java.util.Random;
@Component
public class RandomCoordinateGeneratorPolicyV1 implements RandomCoordinatePolicy {
    public Coordinate generate() {
        double x_gap = 132.0-124.5;
        double y_gap = 38.9-33.0;
        Random random = new Random();
        String random_x = Double.toString(124.5+random.nextDouble(x_gap)); ///7.5는 나올 확률이 없다.. 이거 개선을 해야할까?
        String random_y = Double.toString(33.0+random.nextDouble(y_gap));
        return new Coordinate(random_x,random_y);
    }
}

