package pl.goreit.blog.domain.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Document
public class Account {

    @Id
    private String id;

    private String userId;

    private String firstName;

    private String lastName;

    private Address address;

    private BigDecimal balance;

    private BigDecimal coins;

    private LocalDateTime createdAt;

    public Account(String userId, String firstName, String lastName, Address address, BigDecimal balance, BigDecimal coins) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.balance = balance;
        this.coins = coins;
        this.createdAt = LocalDateTime.now();
    }

    public void increaseBalance(BigDecimal value) {
        this.balance = this.balance.add(value);
    }

    public void decreaseBalance(BigDecimal value) {
        this.balance = this.balance.subtract(value);
    }

    public void increaseCoins(BigDecimal value) {
        this.coins = this.coins.add(value);
    }

    public BigDecimal getCoins() {
        return coins;
    }

    public String getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Address getAddress() {
        return address;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
