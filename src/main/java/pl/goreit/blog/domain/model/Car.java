package pl.goreit.blog.domain.model;

import com.google.common.collect.Lists;
import pl.goreit.api.generated.ProductView;

import java.util.List;

public class Car {

    private Integer No;
    private String name;
    private String mark;
    private String model;
    private Integer year;
    private String vin;
    private List<ProductView> services;

    public Car(Integer No, String name, String mark, String model, Integer year, String vin) {
        this.No = No;
        this.name = name;
        this.mark = mark;
        this.model = model;
        this.year = year;
        this.vin = vin;
        this.services = Lists.newArrayList();
    }

    public boolean addServices(List<ProductView> products) {
        return this.services.addAll(products);
    }

    public boolean removeService(String productTitle) {
        return this.services.removeIf(product -> product.getTitle().equals(productTitle));
    }


    public String getName() {
        return name;
    }

    public String getMark() {
        return mark;
    }

    public String getModel() {
        return model;
    }

    public Integer getYear() {
        return year;
    }

    public String getVin() {
        return vin;
    }

    public List<ProductView> getServices() {
        return services;
    }

    public Integer getNo() {
        return No;
    }
}
