package co.edu.escuelaing.twitterapi.domain;

import java.time.Instant;

public record Post(
        long id,
        String message,
        String authorId,
        String authorName,
        Instant createdAt
) {
}
