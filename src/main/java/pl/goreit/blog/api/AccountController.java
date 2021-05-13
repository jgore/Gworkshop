package pl.goreit.blog.api;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.goreit.api.generated.ProductViewDetails;
import pl.goreit.api.generated.account.CreateAccountRequest;
import pl.goreit.api.generated.product_api.CreateProductRequest;
import pl.goreit.blog.domain.DomainException;
import pl.goreit.blog.domain.model.Account;
import pl.goreit.blog.domain.service.AccountService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping
    @ApiOperation(value = "pobiera  konto")
    public Account getAccount(@RequestParam("userId") String userId) throws DomainException {

        return accountService.findByUserId(userId);
    }

    @PostMapping
    @ApiOperation(value = "dodaje 1 konto", notes = "dodaje konto")
    public Account addAccount(@RequestBody CreateAccountRequest request) {
        return accountService.add(request);
    }
}
