package pl.goreit.blog.domain.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document
public class Message {

    @Id
    private String id;

    private String email;

    private String title;

    private String body;

    @JsonSerialize(using = ToStringSerializer.class)
    private LocalDateTime creationTime;

    public Message(String email, String title, String body) {
        this.email = email;
        this.title = title;
        this.body = body;
        this.creationTime = LocalDateTime.now();
    }

    public String getEmail() {
        return email;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }
}
