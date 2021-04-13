package pl.goreit.blog.domain.converter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import pl.goreit.api.generated.account.CreateAccountRequest;
import pl.goreit.blog.domain.model.Account;
import pl.goreit.blog.domain.model.Address;
import pl.goreit.blog.domain.model.Car;

import java.math.BigDecimal;
import java.util.stream.Collectors;

@Component
public class CreateAccountRequestToAccountConverter implements Converter<CreateAccountRequest, Account> {

    private final ConversionService conversionService;
    @Value("${pricing.default.balance}")
    private String defaultBalance;

    @Value("${pricing.default.coins}")
    private String defaultCoins;

    @Lazy
    public CreateAccountRequestToAccountConverter(ConversionService conversionService) {
        this.conversionService = conversionService;
    }

    @Override
    public Account convert(CreateAccountRequest request) {
        return new Account(request.getUserId(),
                request.getCars().stream()
                        .map(car -> conversionService.convert(car, Car.class))
                        .collect(Collectors.toList()),

                request.getPesel(),
                request.getFirstName(),
                request.getLastName(),
                conversionService.convert(request.getAddress(), Address.class),
                new BigDecimal(defaultBalance),
                new BigDecimal(defaultCoins));
    }
}
