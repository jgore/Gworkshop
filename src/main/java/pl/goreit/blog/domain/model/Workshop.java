package pl.goreit.blog.domain.model;

import com.google.common.collect.Lists;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document
public class Workshop {

    @Id
    private String id;
    private String name;

    private List<Product> products;
    private List<Mechanic> mechanicList;

    private PhotoAlbum photoAlbum;
    private Address address;
    private String nip;

    private Status status;
    private LocalDateTime creationDate;

    public enum Status {
        ACTIVE, INACTIVE, SUSPENDED
    }

    public Workshop(String id, String name, List<Product> products, List<Mechanic> mechanicList, PhotoAlbum photoAlbum, Address address, String nip) {
        this.id = id;
        this.name = name;
        this.products = products;
        this.mechanicList = mechanicList;
        this.photoAlbum = photoAlbum;
        this.address = address;
        this.nip = nip;

        status = Status.ACTIVE;
        this.creationDate = LocalDateTime.now();
    }

    public void activate() {
        this.status = Status.ACTIVE;
    }

    public void suspend() {
        this.status = Status.SUSPENDED;
    }

    public String getId() {
        return id;
    }

    public String getNip() {
        return nip;
    }

    public String getName() {
        return name;
    }

    public List<Mechanic> getMechanicList() {
        return Lists.newArrayList(mechanicList);
    }

    public List<Product> getProducts() {
        return Lists.newArrayList(products);
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public PhotoAlbum getPhotoAlbum() {
        return photoAlbum;
    }

    public Address getAddress() {
        return address;
    }

    public Status getStatus() {
        return status;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

}
