package co.edu.escuelaing.twitterapi.dto;

import java.time.Instant;

public record PostResponse(
        long id,
        String message,
        String authorId,
        String authorName,
        Instant createdAt
) {
}
