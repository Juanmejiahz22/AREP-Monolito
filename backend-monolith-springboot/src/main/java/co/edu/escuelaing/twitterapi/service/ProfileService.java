package co.edu.escuelaing.twitterapi.service;

import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import co.edu.escuelaing.twitterapi.dto.MeResponse;

@Service
public class ProfileService {
    public MeResponse fromJwt(Jwt jwt) {
        return new MeResponse(
                jwt.getSubject(),
                claim(jwt, "name", jwt.getSubject()),
                claim(jwt, "email", null),
                claim(jwt, "nickname", null));
    }

    private String claim(Jwt jwt, String key, String fallback) {
        Object value = jwt.getClaims().get(key);
        return value == null ? fallback : String.valueOf(value);
    }
}
