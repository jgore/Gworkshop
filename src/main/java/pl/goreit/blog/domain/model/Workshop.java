package pl.goreit.blog.domain.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document
public class Workshop {

    @Id
    private String id;
    private String nip;
    private String name;

    private PhotoAlbum photoAlbum;
    private Address address;

    private Status status;
    private LocalDateTime creationDate;

    public enum Status {
        ACTIVE, INACTIVE, SUSPENDED
    }

    public Workshop(String id, String nip, String name, PhotoAlbum photoAlbum, Address address) {
        this.id = id;
        this.nip = nip;
        this.name = name;
        this.photoAlbum = photoAlbum;
        this.address = address;
        status = Status.ACTIVE;
    }

    public void activate(){
        this.status = Status.ACTIVE;
    }

    public void suspend(){
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
