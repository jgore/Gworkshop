package pl.goreit.blog.domain.service;

import pl.goreit.api.generated.account.CreateAccountRequest;
import pl.goreit.blog.domain.model.Account;

public interface AccountService {
    Account findByUserId(String userId);

    Account add(CreateAccountRequest request);

    Account save(Account account);
}
