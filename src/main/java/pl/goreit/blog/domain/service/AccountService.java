package pl.goreit.blog.domain.service;

import pl.goreit.api.generated.OrderlineView;
import pl.goreit.api.generated.account.CreateAccountRequest;
import pl.goreit.blog.domain.DomainException;
import pl.goreit.blog.domain.model.Account;
import pl.goreit.blog.domain.model.Car;
import pl.goreit.blog.domain.model.Product;

import java.util.List;

public interface AccountService {

    Car updateWithServices(List<OrderlineView> services) throws DomainException;

    Account findByUserId(String userId);

    Account add(CreateAccountRequest request);

    Account save(Account account);
}
