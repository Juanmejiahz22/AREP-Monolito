package co.edu.escuelaing.twitterapi.dto;

public record MeResponse(
        String subject,
        String name,
        String email,
        String nickname
) {
}
