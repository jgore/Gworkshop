package pl.goreit.blog.domain.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Document
public class Account {

    @Id
    private String userId;

    private Integer activeCarNo;
    private List<Car> cars;

    private String firstName;
    private String lastName;
    private String pesel;

    private Address address;

    private BigDecimal balance;
    private BigDecimal coins;

    private LocalDateTime createdAt;

    public Account(String userId, List<Car> cars, String pesel, String firstName, String lastName, Address address, BigDecimal balance, BigDecimal coins) {
        this.userId = userId;
        this.cars = cars;

        this.pesel = pesel;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.balance = balance;
        this.coins = coins;
        this.createdAt = LocalDateTime.now();
    }

    public Integer getActiveCarNo() {
        return activeCarNo;
    }

    public boolean addCar(Car car) {
        return this.cars.add(car);
    }

    public boolean removeCar(String vin) {
        return this.cars.
                removeIf(car -> car.getVin().equals(vin));
    }

    public List<Car> getCars() {
        return cars;
    }

    public String getPesel() {
        return pesel;
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
