package pl.goreit.blog.domain.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import pl.goreit.api.generated.account.CreateAccountRequest;
import pl.goreit.blog.domain.converter.CreateAccountRequestToAccountConverter;
import pl.goreit.blog.domain.model.Account;
import pl.goreit.blog.domain.service.AccountService;
import pl.goreit.blog.infrastructure.mongo.AccountRepo;

import java.util.Objects;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepo accountRepo;

    @Autowired
    private ConversionService conversionService;

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
