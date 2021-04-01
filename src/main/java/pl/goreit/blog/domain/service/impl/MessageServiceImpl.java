package pl.goreit.blog.domain.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import pl.goreit.api.generated.message.MessageView;
import pl.goreit.blog.domain.model.Message;
import pl.goreit.blog.domain.service.MessageService;
import pl.goreit.blog.infrastructure.mongo.MessageRepo;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageRepo messageRepo;

    @Autowired
    private ConversionService sellConversionService;

    @Override
    public List<MessageView> findByEmail(String email) {
        List<Message> messages = messageRepo.findByEmail(email);
        return messages
                .stream()
                .map(message -> sellConversionService.convert(message, MessageView.class))
                .collect(Collectors.toList());
    }

    @Override
    public Boolean create(MessageView view) {
        Message message = sellConversionService.convert(view, Message.class);
        messageRepo.save(Objects.requireNonNull(message));
        return true;
    }
}
