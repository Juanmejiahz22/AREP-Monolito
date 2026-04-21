package co.edu.escuelaing.twitterapi.service;

import java.time.Instant;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.security.oauth2.jwt.Jwt;

class ProfileServiceTest {
    private final ProfileService service = new ProfileService();

    @Test
    void shouldMapJwtToMeResponse() {
        Jwt jwt = new Jwt("token", Instant.now(), Instant.now().plusSeconds(60),
                Map.of("alg", "none"), Map.of(
                        "sub", "auth0|abc",
                        "name", "Davi User",
                        "email", "davi@example.com",
                        "nickname", "davi"));
        var response = service.fromJwt(jwt);
        assertThat(response.subject()).isEqualTo("auth0|abc");
        assertThat(response.name()).isEqualTo("Davi User");
    }
}
