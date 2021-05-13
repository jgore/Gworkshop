package pl.goreit.blog.api;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import io.swagger.annotations.AuthorizationScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pl.goreit.api.generated.CreateOrderRequest;
import pl.goreit.api.generated.OrderResponse;
import pl.goreit.api.generated.ReceiveOrderRequest;
import pl.goreit.blog.domain.DomainException;
import pl.goreit.blog.domain.service.OrderService;

import java.security.Principal;
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
    @ApiOperation(value = "get  user orders")
    public List<OrderResponse> getOrders( ) throws DomainException {

        return orderService.findByUserId();
    }


    @PostMapping(value = "/schedule")
    @ApiOperation(value = "schedule new order")
    public OrderResponse schedule(@RequestBody CreateOrderRequest orderRequest) throws DomainException {
        return orderService.schedule(orderRequest);
    }

    @PostMapping(value = "/receive")
    @ApiOperation(value = "schedule new order")
    public OrderResponse receive(@RequestBody ReceiveOrderRequest orderRequest) throws DomainException {
        return orderService.receive(orderRequest);
    }
}
