package co.edu.escuelaing.twitterapi.mapper;

import co.edu.escuelaing.twitterapi.domain.Post;
import co.edu.escuelaing.twitterapi.dto.PostResponse;
import org.springframework.stereotype.Component;

@Component
public class PostMapper {
    public PostResponse toResponse(Post post) {
        return new PostResponse(
                post.id(),
                post.message(),
                post.authorId(),
                post.authorName(),
                post.createdAt());
    }
}
