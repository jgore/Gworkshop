package pl.goreit.blog.domain.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import pl.goreit.api.generated.account.CarView;
import pl.goreit.blog.domain.model.Car;

@Component
public class CarViewToCarConverter  implements Converter<CarView, Car> {
    @Override
    public Car convert(CarView carView) {
        return new Car(carView.getNo(),
                carView.getName(),
                carView.getMark(),
                carView.getModel(),
                carView.getYear(), carView.getVin());
    }
}
