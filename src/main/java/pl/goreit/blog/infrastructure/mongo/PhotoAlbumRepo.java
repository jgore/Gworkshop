package pl.goreit.blog.infrastructure.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import pl.goreit.blog.domain.model.PhotoAlbum;

import java.util.List;

@Repository
public interface PhotoAlbumRepo extends MongoRepository<PhotoAlbum, String> {

    List<PhotoAlbum> findByUserId(String userId);
}
