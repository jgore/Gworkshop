package pl.goreit.blog.domain.converter;

import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import pl.goreit.api.generated.workshop.AddressView;
import pl.goreit.api.generated.workshop.CompanyView;
import pl.goreit.blog.domain.model.Address;
import pl.goreit.blog.domain.model.Company;

@Component
public class CompanyToCompanyViewConverter implements Converter<Company, CompanyView> {

    private final ConversionService conversionService;

    @Lazy
    public CompanyToCompanyViewConverter(ConversionService conversionService) {
        this.conversionService = conversionService;
    }

    @Override
    public CompanyView convert(Company company) {
        return new CompanyView(company.getName(),
                company.getNip(),
                conversionService.convert(company.getAddress(),
                        AddressView.class) );
    }
}
