package pl.goreit.blog.domain.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pl.goreit.api.generated.OrderlineView;
import pl.goreit.api.generated.account.CreateAccountRequest;
import pl.goreit.blog.domain.DomainException;
import pl.goreit.blog.domain.ExceptionCode;
import pl.goreit.blog.domain.model.Account;
import pl.goreit.blog.domain.model.Car;
import pl.goreit.blog.domain.service.AccountService;
import pl.goreit.blog.infrastructure.mongo.AccountRepo;
import pl.goreit.blog.infrastructure.mongo.ProductRepo;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepo accountRepo;

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private ConversionService conversionService;

    @Override
    public Car updateWithServices(List<OrderlineView> orderLineViews) throws DomainException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();

        Account account = accountRepo.findByUserId(userId);
        Integer No = account.getActiveCarNo();
        Car activeCar = account.getCars().stream()
                .filter(car -> car.getNo().equals(No)).findFirst().orElseThrow(() -> new DomainException(ExceptionCode.CAR_NOT_EXIST));

        List<String> serviceNames = orderLineViews.stream()
                .map(OrderlineView::getProductTitle)
                .collect(Collectors.toList());

        List<ProductView> productViews = productRepo.findByTitleIn(serviceNames).stream().map(product -> conversionService.convert(product, ProductView.class)).collect(Collectors.toList());

        activeCar.addServices(productViews);

        accountRepo.save(account);
        return activeCar;
    }

    @Override
    public Account findByUserId(String userId) {
        return accountRepo.findByUserId(userId);
    }

    @Override
    public Account add(CreateAccountRequest request) {
        return accountRepo.save(Objects.requireNonNull(conversionService.convert(request, Account.class)));
    }

    @Override
    public Account save(Account account) {
        return this.accountRepo.save(account);
    }
}
