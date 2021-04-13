package pl.goreit.blog.domain.model;

public class Car {

    private String name;
    private String mark;
    private String model;
    private Integer year;
    private String vin;

    public Car(String name, String mark, String model, Integer year, String vin) {
        this.name = name;
        this.mark = mark;
        this.model = model;
        this.year = year;
        this.vin = vin;
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
}
