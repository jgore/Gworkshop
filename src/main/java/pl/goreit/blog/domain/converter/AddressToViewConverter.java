package pl.goreit.blog.domain.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import pl.goreit.api.generated.workshop.AddressView;
import pl.goreit.blog.domain.model.Address;

@Component
public class AddressToViewConverter implements Converter<Address, AddressView> {

    @Override
    public AddressView convert(Address address) {
        return new AddressView(address.getStreet(),
                address.getStreetNumber(),
                address.getApartmentNumber(),
                address.getPostCode(),
                address.getCity(),
                address.getCountry()
        );
    }
}