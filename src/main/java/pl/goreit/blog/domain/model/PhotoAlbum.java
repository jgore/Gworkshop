package pl.goreit.blog.domain.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Document
public class PhotoAlbum {

    @Id
    private String id;
    private String userId;
    private String name;
    private List<List<Byte>> byteList;
    private LocalDateTime createdAt;
    private LocalDateTime updateAt;

    public PhotoAlbum(String userId, String name, List<List<Byte>> byteList  ) {
        this.userId = userId;
        this.name = name;
        this.byteList = byteList;
        this.createdAt = LocalDateTime.now();
        this.updateAt = LocalDateTime.now();
    }

    public String getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public List<List<Byte>> getPhotos() {
        return new ArrayList<>(byteList);
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdateAt() {
        return updateAt;
    }
}
