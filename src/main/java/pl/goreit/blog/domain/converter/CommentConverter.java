package pl.goreit.blog.domain.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import pl.goreit.api.generated.CommentView;
import pl.goreit.blog.domain.model.Comment;

@Component
public class CommentConverter implements Converter<Comment, CommentView> {

    @Override
    public CommentView convert(Comment comment) {
        return new CommentView(comment.getUserId(), comment.getText());

    }
}
