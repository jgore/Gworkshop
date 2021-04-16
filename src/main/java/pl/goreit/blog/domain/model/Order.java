package pl.goreit.blog.domain.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Document
public class Order {

    @Id
    private String id;

    private String userId;

    private String sellerId;

    private List<OrderLine> orderLines;

    private BigDecimal totalCost;

    @JsonSerialize(using = ToStringSerializer.class)
    private LocalDateTime creationTime;

    public Order() {
    }

    public Order(String id, String sellerId, String userId, List<OrderLine> orderLines, LocalDateTime creationTime) {
        this.id = id;
        this.sellerId = sellerId;
        this.userId = userId;
        this.orderLines = orderLines;
        this.creationTime = creationTime;
        orderLines.forEach(orderLine -> {
            this.totalCost = orderLine.getPrice().multiply(BigDecimal.valueOf(orderLine.getAmount()));
        });
    }


    public String getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String getSellerId() {
        return sellerId;
    }

    public List<OrderLine> getOrderLines() {
        return orderLines;
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }
}
