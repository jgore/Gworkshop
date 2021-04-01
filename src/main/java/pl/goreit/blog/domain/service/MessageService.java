package pl.goreit.blog.domain.service;

import pl.goreit.api.generated.message.MessageView;

import java.util.List;

public interface MessageService {
    List<MessageView> findByEmail(String email);

    Boolean create(MessageView messageView);
}
