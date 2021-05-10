package pl.goreit.blog.domain.converter;

import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import pl.goreit.api.generated.account.PersonView;
import pl.goreit.blog.domain.model.Address;
import pl.goreit.blog.domain.model.Person;

@Component
public class PersonViewToPersonConverter implements Converter<PersonView, Person> {


    private final ConversionService conversionService;

    @Lazy
    public PersonViewToPersonConverter(ConversionService conversionService) {
        this.conversionService = conversionService;
    }

    @Override
    public Person convert(PersonView view) {
        return new Person(view.getFirstName(),
                view.getLastName(),
                view.getPesel(),
                conversionService.convert(view.getAddress(),
                        Address.class));
    }
}
