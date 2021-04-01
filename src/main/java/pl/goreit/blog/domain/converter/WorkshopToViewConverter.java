package pl.goreit.blog.domain.converter;

import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import pl.goreit.api.generated.workshop.AddressView;
import pl.goreit.api.generated.workshop.WorkshopView;
import pl.goreit.blog.domain.model.Workshop;

@Component
public class WorkshopToViewConverter  implements Converter<Workshop, WorkshopView> {

    private final ConversionService conversionService;

    @Lazy
    public WorkshopToViewConverter(ConversionService conversionService) {
        this.conversionService = conversionService;
    }

    @Override
    public WorkshopView convert(Workshop workshop) {
        return new WorkshopView(workshop.getNip(),
                workshop.getName(),
                conversionService.convert(workshop.getAddress(), AddressView.class));
    }
}
