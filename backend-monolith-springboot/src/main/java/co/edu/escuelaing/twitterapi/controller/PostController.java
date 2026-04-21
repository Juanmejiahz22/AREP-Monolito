package co.edu.escuelaing.twitterapi.controller;

import co.edu.escuelaing.twitterapi.dto.CreatePostRequest;
import co.edu.escuelaing.twitterapi.dto.PostResponse;
import co.edu.escuelaing.twitterapi.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    private final PostService service;

    public PostController(PostService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Read the public global feed")
    public List<PostResponse> findAll() {
        return service.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a protected short post", security = @SecurityRequirement(name = "bearerAuth"))
    public PostResponse create(@Valid @RequestBody CreatePostRequest request, @AuthenticationPrincipal Jwt jwt) {
        return service.create(request, jwt);
    }
}
