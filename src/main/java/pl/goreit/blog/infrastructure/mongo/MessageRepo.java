package pl.goreit.blog.infrastructure.mongo;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import pl.goreit.blog.domain.model.Message;

import java.util.List;

@Repository
public interface MessageRepo extends MongoRepository<Message, String> {

    List<Message> findByEmail(String email);
}
