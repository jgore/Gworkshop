package pl.goreit.blog.domain.model;

public class Address {

    private String street;
    private String  streetNumber;
    private String apartmentNumber;
    private String postCode;
    private String city;
    private String country;

    public Address(String street, String  streetNumber, String apartmentNumber, String postCode, String city, String country) {
        this.street = street;
        this.streetNumber = streetNumber;
        this.apartmentNumber = apartmentNumber;
        this.postCode = postCode;
        this.city = city;
        this.country = country;
    }

    public String getStreet() {
        return street;
    }

    public String  getStreetNumber() {
        return streetNumber;
    }

    public String getApartmentNumber() {
        return apartmentNumber;
    }

    public String getPostCode() {
        return postCode;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    @Override
    public String toString() {
        return getStreet()+ " "+getStreetNumber()+"/"+getApartmentNumber() +",\n" +
                " "+getPostCode()+ " "+getCity() +" "+getCountry();
    }
}
