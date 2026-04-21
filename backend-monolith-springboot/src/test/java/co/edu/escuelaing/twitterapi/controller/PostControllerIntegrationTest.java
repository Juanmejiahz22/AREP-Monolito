package co.edu.escuelaing.twitterapi.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PostControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JwtDecoder jwtDecoder;

    @Test
    void shouldAllowPublicFeedWithoutToken() throws Exception {
        mockMvc.perform(get("/api/posts"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldRejectCreateWhenTokenIsMissing() throws Exception {
        mockMvc.perform(post("/api/posts")
                        .contentType("application/json")
                .content("{\"message\":\"hello\"}"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void shouldCreateProtectedPost() throws Exception {
        mockMvc.perform(post("/api/posts")
                        .with(jwt().jwt(jwt -> jwt.claim("sub", "auth0|abc").claim("name", "Davi User")))
                        .contentType("application/json")
                .content("{\"message\":\"hello\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("hello"))
                .andExpect(jsonPath("$.authorName").value("Davi User"));
    }

    @Test
    void shouldValidateMessageLength() throws Exception {
        String payload = "{\"message\":\"" + "x".repeat(141) + "\"}";
        mockMvc.perform(post("/api/posts")
                        .with(jwt().jwt(jwt -> jwt.claim("sub", "auth0|abc").claim("name", "Davi User")))
                        .contentType("application/json")
                        .content(payload))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("message must be at most 140 characters"));
    }
}
