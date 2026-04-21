package co.edu.escuelaing.twitterapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreatePostRequest(
        @NotBlank(message = "message is required")
        @Size(max = 140, message = "message must be at most 140 characters")
        String message
) {
}
