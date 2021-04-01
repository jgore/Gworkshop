package pl.goreit.blog.domain.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import pl.goreit.api.generated.workshop.AddressView;
import pl.goreit.blog.domain.model.Address;

@Component
public class AddressWorkshopViewtToEntityconverter implements Converter<AddressView, Address> {

    @Override
    public Address convert(AddressView addressView) {
        return new Address(addressView.getStreet(),
                addressView.getStreetNumber(),
                addressView.getApartmentNumber(),
                addressView.getPostCode(),
                addressView.getCity(),
                addressView.getCountry()
        );
    }
}