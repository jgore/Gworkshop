package pl.goreit.blog.api;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import io.swagger.annotations.AuthorizationScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pl.goreit.api.generated.CreateOrderRequest;
import pl.goreit.api.generated.OrderResponse;
import pl.goreit.blog.domain.DomainException;
import pl.goreit.blog.domain.service.OrderService;

import java.util.List;

@RestController
@RequestMapping("orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/{id}")
    @ApiOperation(value = "get order by id")
    public OrderResponse getOrder(@PathVariable("id") String id) throws DomainException {
        return orderService.findById(id);
    }

    @GetMapping()
    @ApiOperation(value = "get  user orders", authorizations = @Authorization(value = "oauth2", scopes = @AuthorizationScope(description = "write", scope = "write")))
    public List<OrderResponse> getOrders() throws DomainException {

        return orderService.findByUserId();
    }


    @PostMapping(value = "/add", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE, MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "add new order")
    public OrderResponse addOrder(@RequestBody CreateOrderRequest orderRequest) throws DomainException {
        return orderService.create(orderRequest);
    }

}
