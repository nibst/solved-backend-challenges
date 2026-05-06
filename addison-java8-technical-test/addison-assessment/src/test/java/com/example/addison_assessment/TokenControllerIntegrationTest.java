package com.example.addison_assessment;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.concurrent.CompletableFuture;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.example.addison_assessment.controller.TokenController;
import com.example.addison_assessment.exceptions.AuthException;
import com.example.addison_assessment.exceptions.UserTokenException;
import com.example.addison_assessment.model.UserToken;
import com.example.addison_assessment.service.ISimpleAsyncTokenService;

@WebMvcTest(TokenController.class)
class TokenControllerIntegrationTest {

    private static final String URL = "/api/v1/tokens";
 
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ISimpleAsyncTokenService tokenService;
 
    @Test
    @DisplayName("POST /api/v1/tokens with valid credentials returns 200 and token")
    void issueToken_validCredentials_returns200() throws Exception {
        String token = "house_2024-01-01T10:00:00+0000";
        when(tokenService.issueToken(any()))
                .thenReturn(CompletableFuture.completedFuture(new UserToken(token)));
        //Perform the request and capture the async result
        MvcResult mvcResult = mockMvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    { "username": "house", "password": "HOUSE" }
                    """))
            .andExpect(request().asyncStarted()) // assert async was started
            .andReturn();
        //Dispatch the async result and then assert
        mockMvc.perform(asyncDispatch(mvcResult))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.token").value(token));
    }

    @Test
    @DisplayName("Invalid credentials return 401 Unauthorized")
    void issueToken_invalidCredentials_returns401() throws Exception {
        when(tokenService.issueToken(any()))
                .thenReturn(CompletableFuture.failedFuture(
                        new AuthException("Invalid credentials for user: house")));

        //Perform the request and capture the async result
        MvcResult mvcResult = mockMvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    { "username": "house", "password": "House" }
                    """))
            .andExpect(request().asyncStarted()) // assert async was started
            .andReturn();
        //Dispatch the async result and then assert
        mockMvc.perform(asyncDispatch(mvcResult))
            .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("userId starting with 'A' returns 403 Forbidden")
    void issueToken_userIdStartsWithA_returns403() throws Exception {
        when(tokenService.issueToken(any()))
            .thenReturn(CompletableFuture.failedFuture(
                        new UserTokenException("Token cannot be granted for userId starting with 'A': Alice")));

        // Perform the request and capture the async result
        MvcResult mvcResult = mockMvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    { "username": "Alice", "password": "ALICE" }
                    """))
            .andExpect(request().asyncStarted()) // assert async was started
            .andReturn();

        // Dispatch the async result and then assert
        mockMvc.perform(asyncDispatch(mvcResult))
            .andExpect(status().isForbidden());
    }
}
