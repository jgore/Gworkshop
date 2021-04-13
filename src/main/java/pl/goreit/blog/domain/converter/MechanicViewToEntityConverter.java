package pl.goreit.blog.domain.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import pl.goreit.api.generated.workshop.MechanicView;
import pl.goreit.blog.domain.model.Mechanic;

@Component
public class MechanicViewToEntityConverter  implements Converter<MechanicView, Mechanic> {

    @Override
    public Mechanic convert(MechanicView view) {
        return new Mechanic(
                view.getLogin(),
                view.getFirstName(),
                view.getLastName(),
                view.getPesel());
    }
}
