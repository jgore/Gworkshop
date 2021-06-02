package pl.goreit.blog.domain.model;

public class Company {

    private String name;
    private String nip;

    private Address address;

    public Company(String name, String nip, Address address) {
        this.name = name;
        this.nip = nip;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public String getNip() {
        return nip;
    }

    public Address getAddress() {
        return address;
    }
}
