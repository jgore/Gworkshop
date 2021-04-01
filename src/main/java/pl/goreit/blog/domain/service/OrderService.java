package pl.goreit.blog.domain.service;

import pl.goreit.api.generated.CreateOrderRequest;
import pl.goreit.api.generated.OrderResponse;
import pl.goreit.blog.domain.DomainException;

import java.util.List;

public interface OrderService {

    OrderResponse findById(String id) throws DomainException;
    List<OrderResponse> findByUserId(String userId) throws DomainException;

    OrderResponse create(CreateOrderRequest orderRequest) throws DomainException;
}
