package pl.goreit.blog.domain.service;

import org.bson.types.ObjectId;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import pl.goreit.api.generated.CreateOrderRequest;
import pl.goreit.api.generated.OrderLineRequest;
import pl.goreit.api.generated.OrderResponse;
import pl.goreit.api.generated.ReceiveOrderRequest;
import pl.goreit.blog.domain.DomainException;
import pl.goreit.blog.domain.ExceptionCode;
import pl.goreit.blog.domain.generator.InvoiceGenerator;
import pl.goreit.blog.domain.model.*;
import pl.goreit.blog.domain.mq.MqOrderService;
import pl.goreit.blog.infrastructure.mongo.OrderRepo;
import pl.goreit.blog.infrastructure.mongo.ProductRepo;
import pl.goreit.blog.infrastructure.mongo.WorkshopRepo;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderService {

    @Autowired
    private ConversionService sellConversionService;

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private MqOrderService mqOrderService;

    @Autowired
    private ProductService productService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private WorkshopRepo workshopRepo;

    public OrderResponse findById(String id) throws DomainException {
        Order order = orderRepo.findById(id).orElseThrow(() -> new DomainException(ExceptionCode.ORDER_NOT_FOUND));
        return sellConversionService.convert(order, OrderResponse.class);
    }

    public List<OrderResponse> findByUserId() {

        KeycloakAuthenticationToken authentication = (KeycloakAuthenticationToken)
                SecurityContextHolder.getContext().getAuthentication();

        List<Order> ordersByUser = orderRepo.findByUserId(authentication.getName());
        return ordersByUser.stream()
                .map(order -> sellConversionService.convert(order, OrderResponse.class))
                .collect(Collectors.toList());
    }

    public OrderResponse schedule(CreateOrderRequest createOrderRequest) throws DomainException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();

        List<OrderLineRequest> orderLineRequests = createOrderRequest.getOrderlines();

        if (orderLineRequests == null || orderLineRequests.isEmpty()) {
            throw new DomainException(ExceptionCode.ORDER_NOT_HAVE_ORDERLINES);
        }

        ObjectId orderId = ObjectId.get();

        List<OrderLine> orderlines = orderLineRequests.stream()
                .map(orderLineView -> {

                    //@FIXME get all upper
                    Product product = productRepo.findByTitle(orderLineView.getProductTitle()).get();
                    return new OrderLine(orderId.toString(), product.getWorkshopId(), product.getTitle(), orderLineView.getAmount(), product.getPrice());
                })
                .collect(Collectors.toList());


        Order order = new Order(createOrderRequest.getSellerId(), userId, orderlines);
        orderRepo.save(order);

        accountService.updateWithServices(order.getOrderLines());

        return sellConversionService.convert(order, OrderResponse.class);
    }

    public OrderResponse receive(ReceiveOrderRequest receiveOrderRequest) throws DomainException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();

        String orderId = receiveOrderRequest.getOrderId();
        Order order = orderRepo.findById(orderId).orElseThrow(() -> new DomainException(ExceptionCode.ORDER_NOT_FOUND));

        Workshop workshop = workshopRepo.findByName(order.getWorkshopName());

        Account user = accountService.findByUserId(userId);
        Person person = user.getPerson();

        Integer invoiceCounter = workshop.getInvoiceCounter();
        InvoiceGenerator invoiceGenerator = new InvoiceGenerator(order, workshop, person);
        invoiceGenerator.generate(invoiceCounter + "_" + LocalDate.now().getYear() + "__" + order.getWorkshopName() +"_"+order.getId() +".pdf");

        order.receiveOrder();
        orderRepo.save(order);
        workshop.increaseInvoiceCounter();
        workshopRepo.save(workshop);

        return sellConversionService.convert(order, OrderResponse.class);
    }


}
