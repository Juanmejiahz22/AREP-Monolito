package co.edu.escuelaing.twitterapi.controller;

import co.edu.escuelaing.twitterapi.dto.MeResponse;
import co.edu.escuelaing.twitterapi.service.ProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/me")
public class ProfileController {
    private final ProfileService service;

    public ProfileController(ProfileService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Read the authenticated user profile", security = @SecurityRequirement(name = "bearerAuth"))
    public MeResponse me(@AuthenticationPrincipal Jwt jwt) {
        return service.fromJwt(jwt);
    }
}
