package pl.goreit.blog.domain.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import pl.goreit.api.generated.*;
import pl.goreit.blog.domain.DomainException;
import pl.goreit.blog.domain.ExceptionCode;
import pl.goreit.blog.domain.model.Account;
import pl.goreit.blog.domain.model.Car;
import pl.goreit.blog.domain.model.Order;
import pl.goreit.blog.domain.mq.MqOrderService;
import pl.goreit.blog.domain.service.AccountService;
import pl.goreit.blog.domain.service.OrderService;
import pl.goreit.blog.domain.service.ProductService;
import pl.goreit.blog.infrastructure.mongo.OrderRepo;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderServiceImpl implements OrderService {

    @Autowired
    private ConversionService sellConversionService;

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private MqOrderService mqOrderService;

    @Autowired
    private PricingServiceImpl pricingService;

    @Autowired
    private ProductService productService;

    @Autowired
    private AccountService accountService;

    @Override
    public OrderResponse findById(String id) throws DomainException {
        Order order = orderRepo.findById(id).orElseThrow(() -> new DomainException(ExceptionCode.GOREIT_04));
        return sellConversionService.convert(order, OrderResponse.class);
    }

    @Override
    public List<OrderResponse> findByUserId(String userId) {
        List<Order> ordersByUser = orderRepo.findByUserId(userId);
        return ordersByUser.stream()
                .map(order -> sellConversionService.convert(order, OrderResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public OrderResponse create(CreateOrderRequest createOrderRequest) throws DomainException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();

        //TODO FIXME remove USER ID from request
        createOrderRequest.withUserId(userId);

        List<OrderLineRequest> orderLineRequests = createOrderRequest.getOrderlines();

        if (orderLineRequests == null || orderLineRequests.isEmpty()) {
            throw new DomainException(ExceptionCode.GOREIT_06);
        }

        OrderResponse orderResponse = null;

        try {
            orderResponse = mqOrderService.sendOrder(createOrderRequest);
            pricingService.coinsSettlement(orderResponse);
            productService.updateAfterOrdered(orderResponse);
            accountService.updateWithServices(orderResponse.getOrderlineViews());

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }



        return orderResponse;
    }
}
