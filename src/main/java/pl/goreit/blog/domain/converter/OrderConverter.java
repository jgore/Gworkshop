package pl.goreit.blog.domain.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import pl.goreit.api.generated.OrderResponse;
import pl.goreit.api.generated.OrderlineView;
import pl.goreit.blog.domain.model.Order;

import java.util.stream.Collectors;

@Component
public class OrderConverter implements Converter<Order, OrderResponse> {

    @Override
    public OrderResponse convert(Order order) {
        return new OrderResponse(order.getId(),
                order.getUserId(),
                order.getWorkshopName(),
                order.getCreationTime().toString(),
                order.getTotalCost(),
                order.getOrderLines().stream()
                        .map(orderProduct -> new OrderlineView(orderProduct.getSellerId(), orderProduct.getProductTitle(), orderProduct.getAmount(), orderProduct.getPrice()))
                        .collect(Collectors.toList()));
    }
}
