package co.edu.escuelaing.twitterapi.security;

import java.time.Instant;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.security.oauth2.jwt.Jwt;

class AudienceValidatorTest {
    private final AudienceValidator validator = new AudienceValidator("https://twitter-api");

    @Test
    void shouldAcceptExpectedAudience() {
        Jwt jwt = jwtWithAudience(List.of("https://twitter-api"));
        assertThat(validator.validate(jwt).hasErrors()).isFalse();
    }

    @Test
    void shouldRejectUnexpectedAudience() {
        Jwt jwt = jwtWithAudience(List.of("https://other-api"));
        assertThat(validator.validate(jwt).hasErrors()).isTrue();
    }

    private Jwt jwtWithAudience(List<String> audience) {
        return new Jwt("token", Instant.now(), Instant.now().plusSeconds(60),
                Map.of("alg", "none"), Map.of("sub", "auth0|123", "aud", audience));
    }
}
