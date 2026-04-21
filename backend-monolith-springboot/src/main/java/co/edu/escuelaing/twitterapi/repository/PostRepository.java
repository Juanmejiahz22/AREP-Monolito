package co.edu.escuelaing.twitterapi.repository;

import java.util.List;

import co.edu.escuelaing.twitterapi.domain.Post;

public interface PostRepository {
    Post save(Post post);
    List<Post> findAllOrderByCreatedAtDesc();
}
