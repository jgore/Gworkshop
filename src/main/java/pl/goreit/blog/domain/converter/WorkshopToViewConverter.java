package pl.goreit.blog.domain.converter;

import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import pl.goreit.api.generated.ProductView;
import pl.goreit.api.generated.workshop.AddressView;
import pl.goreit.api.generated.workshop.MechanicView;
import pl.goreit.api.generated.workshop.WorkshopView;
import pl.goreit.blog.domain.model.Product;
import pl.goreit.blog.domain.model.Workshop;

import java.util.stream.Collectors;

@Component
public class WorkshopToViewConverter implements Converter<Workshop, WorkshopView> {

    private final ConversionService conversionService;

    @Lazy
    public WorkshopToViewConverter(ConversionService conversionService) {
        this.conversionService = conversionService;
    }

    @Override
    public WorkshopView convert(Workshop workshop) {
        return new WorkshopView(workshop.getNip(),
                workshop.getName(),
                workshop.getProducts().stream()
                        .map(product -> conversionService.convert(product, ProductView.class))
                        .collect(Collectors.toList()),
                workshop.getMechanicList().stream()
                        .map(mechanic -> conversionService.convert(mechanic, MechanicView.class))
                        .collect(Collectors.toList()),
                conversionService.convert(workshop.getAddress(), AddressView.class));
    }
}
