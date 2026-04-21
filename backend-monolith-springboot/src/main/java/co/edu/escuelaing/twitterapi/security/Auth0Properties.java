package co.edu.escuelaing.twitterapi.security;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "auth0")
public record Auth0Properties(
        String issuerUri,
        String audience,
        String frontendLocalUrl,
        String frontendProdUrl
) {
}
