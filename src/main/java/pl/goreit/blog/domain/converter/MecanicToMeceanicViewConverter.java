package pl.goreit.blog.domain.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import pl.goreit.api.generated.workshop.MechanicView;
import pl.goreit.blog.domain.model.Mechanic;

@Component
public class MecanicToMeceanicViewConverter implements Converter<Mechanic, MechanicView> {

    @Override
    public MechanicView convert(Mechanic mechanic) {
        return new MechanicView(
                mechanic.getLogin(),
                mechanic.getFirstName(),
                mechanic.getLastName(),
                mechanic.getPesel()
        );
    }
}
