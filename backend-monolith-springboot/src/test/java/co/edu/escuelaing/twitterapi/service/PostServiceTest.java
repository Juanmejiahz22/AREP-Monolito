package co.edu.escuelaing.twitterapi.service;

import java.time.Instant;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.security.oauth2.jwt.Jwt;

import co.edu.escuelaing.twitterapi.dto.CreatePostRequest;
import co.edu.escuelaing.twitterapi.mapper.PostMapper;
import co.edu.escuelaing.twitterapi.repository.InMemoryPostRepository;

class PostServiceTest {

    private final PostService service = new PostService(new InMemoryPostRepository(), new PostMapper());

    @Test
    void shouldCreatePostUsingJwtClaims() {
        var response = service.create(new CreatePostRequest("Hello world"), buildJwt());
        assertThat(response.message()).isEqualTo("Hello world");
        assertThat(response.authorId()).isEqualTo("auth0|abc");
        assertThat(response.authorName()).isEqualTo("Davi User");
    }

    @Test
    void shouldReturnNewestPostsFirst() {
        service.create(new CreatePostRequest("first"), buildJwt());

        long waitUntil = System.nanoTime() + 10_000_000L;
        while (System.nanoTime() < waitUntil) {
            Thread.onSpinWait();
        }

        service.create(new CreatePostRequest("second"), buildJwt());

        assertThat(service.findAll())
                .extracting("message")
                .containsExactly("second", "first");
    }

    private Jwt buildJwt() {
        return new Jwt("token", Instant.now(), Instant.now().plusSeconds(60),
                Map.of("alg", "none"), Map.of("sub", "auth0|abc", "name", "Davi User"));
    }
}
