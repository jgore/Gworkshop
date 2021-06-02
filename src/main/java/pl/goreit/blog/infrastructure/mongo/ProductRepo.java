package pl.goreit.blog.infrastructure.mongo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import pl.goreit.blog.domain.model.Product;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepo extends MongoRepository<Product, String> {

    Optional<Product> findByTitle(String title);

    @Query("{'title':{'$regex':'?0','$options':'i'}}")
    Page<Product> findByTitle(String title, Pageable page);

    List<Product> findByTitleIn(List<String> strings);
}
