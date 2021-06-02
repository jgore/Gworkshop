package pl.goreit.blog.infrastructure.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import pl.goreit.blog.domain.model.Workshop;

@Repository
public interface WorkshopRepo extends MongoRepository<Workshop, String> {

    Workshop findByName(String name);

    Workshop findByOwner(String name);
}
