package pl.goreit.blog.domain.model;

import com.google.common.collect.Lists;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import pl.goreit.blog.domain.DomainException;
import pl.goreit.blog.domain.ExceptionCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Document
public class Product {

    @Id
    private String id;
    private String sellerId;
    @Indexed(unique = true)
    private String title;
    private String text;
    private BigDecimal price;
    private List<String> boughtByList;
    private List<Comment> comments;
    private PhotoAlbum photoAlbum;
    private Status status;

    private LocalDateTime creationDate;

    public Product(String sellerId, String title, String text, BigDecimal price, PhotoAlbum photoAlbum) {
        this.sellerId = sellerId;
        this.title = title;
        this.text = text;
        this.price = price;
        this.photoAlbum = photoAlbum;
        this.status = Status.ACTIVE;
        this.creationDate = LocalDateTime.now();
        this.comments = Lists.newArrayList();
        this.boughtByList = Lists.newArrayList();
    }

    public boolean addComment(Comment comment) throws DomainException {
        if (Status.ACTIVE != getStatus()) {
            throw new DomainException(ExceptionCode.GOREIT_03);
        }
        return comments.add(comment);
    }



    public void updateBoughtBy(String userId) {
        this.boughtByList.add(userId);
    }


    public PhotoAlbum getPhotoAlbum() {
        return photoAlbum;
    }

    public List<String> getBoughtByList() {
        return boughtByList;
    }

    public String getId() {
        return id;
    }

    public String getSellerId() {
        return sellerId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public Status getStatus() {
        return status;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public List<Comment> getComments() {
        return Lists.newArrayList(comments);
    }


    public enum Status {
        ACTIVE, SOLD, INACTIVE
    }
}
