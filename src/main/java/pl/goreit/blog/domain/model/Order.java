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

    private String workshopName;

    //@TODO move to invoice - api

    private String invoiceFileName;

    private List<OrderLine> orderLines;

    private BigDecimal totalCost;

    private Status status;

    @JsonSerialize(using = ToStringSerializer.class)
    private LocalDateTime creationTime;

    public Order() {
    }

    public Order(String workshopName, String userId, List<OrderLine> orderLines) {
        this.workshopName = workshopName;
        this.userId = userId;
        this.orderLines = orderLines;
        this.creationTime = LocalDateTime.now();
        this.totalCost = BigDecimal.valueOf(0);
        orderLines.forEach(orderLine -> {
            this.totalCost = this.totalCost.add(orderLine.getPrice().multiply(BigDecimal.valueOf(orderLine.getAmount())));
        });
        this.status = Status.SCHEDULED;
    }

    public Status getStatus() {
        return status;
    }

    public void receiveOrder() {
        this.status = Status.RECEIVED;
    }

    public void setInvoiceFileName(String invoiceFileName) {
        this.invoiceFileName = invoiceFileName;
    }

    public void rejectOrder() {
        this.status = Status.REJECTED;
    }

    public void completeOrder() {
        this.status = Status.COMPLETED;
    }

    public String getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String getWorkshopName() {
        return workshopName;
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

    public enum Status {
        SCHEDULED, REJECTED, RECEIVED, COMPLETED
    }
}
