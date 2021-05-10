package pl.goreit.blog.domain.converter;

import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import pl.goreit.api.generated.workshop.CompanyView;
import pl.goreit.blog.domain.model.Address;
import pl.goreit.blog.domain.model.Company;

@Component
public class CompanyViewToCompanyConverter implements Converter<CompanyView, Company> {

    private final ConversionService conversionService;

    @Lazy
    public CompanyViewToCompanyConverter(ConversionService conversionService) {
        this.conversionService = conversionService;
    }

    @Override
    public Company convert(CompanyView view) {
        return new Company(view.getName(), view.getNip(), conversionService.convert(view.getAddress(), Address.class));
    }
}
