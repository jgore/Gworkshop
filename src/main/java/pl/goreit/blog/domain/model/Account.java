package pl.goreit.blog.domain.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document
public class Account {

    @Id
    private String userId;
    private Person person;

    private Integer activeCarNo;
    private List<Car> cars;

    private LocalDateTime createdAt;

    public Account(String userId, List<Car> cars, Person person) {
        this.userId = userId;
        this.cars = cars;
        if (!cars.isEmpty()) {
            this.activeCarNo = 1;
        }
        this.person = person;
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

    public String getUserId() {
        return userId;
    }

    public Person getPerson() {
        return person;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
