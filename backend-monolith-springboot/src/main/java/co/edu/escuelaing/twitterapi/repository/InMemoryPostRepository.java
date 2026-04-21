package co.edu.escuelaing.twitterapi.repository;

import co.edu.escuelaing.twitterapi.domain.Post;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class InMemoryPostRepository implements PostRepository {
    private final AtomicLong sequence = new AtomicLong(0);
    private final CopyOnWriteArrayList<Post> posts = new CopyOnWriteArrayList<>();

    @Override
    public Post save(Post post) {
        Post stored = new Post(
                sequence.incrementAndGet(),
                post.message(),
                post.authorId(),
                post.authorName(),
                Instant.now());
        posts.add(stored);
        return stored;
    }

    @Override
    public List<Post> findAllOrderByCreatedAtDesc() {
        return posts.stream()
                .sorted(Comparator.comparing(Post::createdAt).reversed())
                .toList();
    }
}
