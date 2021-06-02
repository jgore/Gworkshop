package pl.goreit.blog.domain.model;

public class Person {

    private String firstName;
    private String lastName;
    private String pesel;

    private Address address;

    public Person(String firstName, String lastName, String pesel, Address address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.pesel = pesel;
        this.address = address;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPesel() {
        return pesel;
    }

    public Address getAddress() {
        return address;
    }
}
