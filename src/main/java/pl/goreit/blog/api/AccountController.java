package pl.goreit.blog.api;

import io.swagger.annotations.ApiOperation;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import pl.goreit.api.generated.account.CreateAccountRequest;
import pl.goreit.blog.domain.model.Account;
import pl.goreit.blog.domain.service.AccountService  ;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping
    @ApiOperation(value = "pobiera  konto")
    public Account getAccount() {

        KeycloakAuthenticationToken authentication = (KeycloakAuthenticationToken)
                SecurityContextHolder.getContext().getAuthentication();

        return accountService.findByUserId(authentication.getName());
    }

    @PostMapping
    @ApiOperation(value = "dodaje 1 konto", notes = "dodaje konto")
    public Account addAccount(@RequestBody CreateAccountRequest request) {
        return accountService.add(request);
    }
}
