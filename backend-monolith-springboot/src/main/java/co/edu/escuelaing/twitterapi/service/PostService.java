package co.edu.escuelaing.twitterapi.service;

import java.time.Instant;
import java.util.List;

import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import co.edu.escuelaing.twitterapi.domain.Post;
import co.edu.escuelaing.twitterapi.dto.CreatePostRequest;
import co.edu.escuelaing.twitterapi.dto.PostResponse;
import co.edu.escuelaing.twitterapi.mapper.PostMapper;
import co.edu.escuelaing.twitterapi.repository.PostRepository;

@Service
public class PostService {
    private final PostRepository repository;
    private final PostMapper mapper;

    public PostService(PostRepository repository, PostMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public PostResponse create(CreatePostRequest request, Jwt jwt) {
        Post draft = new Post(0, request.message().trim(), jwt.getSubject(), resolveName(jwt), Instant.now());
        return mapper.toResponse(repository.save(draft));
    }

    public List<PostResponse> findAll() {
        return repository.findAllOrderByCreatedAtDesc().stream()
                .map(mapper::toResponse)
                .toList();
    }

    private String resolveName(Jwt jwt) {
        Object name = jwt.getClaims().getOrDefault("name", jwt.getClaims().getOrDefault("nickname", jwt.getSubject()));
        return String.valueOf(name);
    }
}
