package pl.goreit.blog.domain.model;

import com.google.common.collect.Lists;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import pl.goreit.api.generated.ProductView;

import java.time.LocalDateTime;
import java.util.List;

@Document
public class Workshop {

    @Id
    private String id;
    private String owner;
    @Indexed(unique = true)
    private String name;

    private List<ProductView> products;
    private List<Mechanic> mechanicList;

    private PhotoAlbum photoAlbum;

    private Company company;
    private Integer invoiceCounter;

    private Status status;
    private LocalDateTime creationDate;

    public enum Status {
        ACTIVE, INACTIVE, SUSPENDED
    }

    public Workshop(String id, String name, String owner, Company company, List<ProductView> products, List<Mechanic> mechanicList, PhotoAlbum photoAlbum) {
        this.id = id;
        this.name = name;
        this.owner = owner;
        this.company = company;
        this.products = products;
        this.mechanicList = mechanicList;
        this.photoAlbum = photoAlbum;

        this.status = Status.ACTIVE;
        this.creationDate = LocalDateTime.now();
        this.invoiceCounter = 1;
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

    public String getOwner() {
        return owner;
    }

    public Company getCompany() {
        return company;
    }

    public String getName() {
        return name;
    }

    public List<Mechanic> getMechanicList() {
        return Lists.newArrayList(mechanicList);
    }

    public List<ProductView> getProducts() {
        return Lists.newArrayList(products);
    }

    public boolean addProduct(ProductView productView) {
        return this.products.add(productView);
    }

    public boolean removeProduct(String productTitle) {
        return this.products.removeIf(productView -> productTitle.equals(productView.getTitle()));
    }

    public PhotoAlbum getPhotoAlbum() {
        return photoAlbum;
    }

    public Status getStatus() {
        return status;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public Integer getInvoiceCounter() {
        return invoiceCounter;
    }

    public void increaseInvoiceCounter() {
        invoiceCounter++;
    }
}
