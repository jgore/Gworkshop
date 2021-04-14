package pl.goreit.blog.infrastructure.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import pl.goreit.blog.domain.model.Product;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepo extends MongoRepository<Product, String> {

    Optional<Product> findByTitle(String title);

    List<Product> findByTitleIn(List<String> strings);

}
