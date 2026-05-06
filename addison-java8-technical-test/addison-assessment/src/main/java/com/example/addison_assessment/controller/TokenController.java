package com.example.addison_assessment.controller;

import java.util.concurrent.CompletableFuture;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.addison_assessment.model.*;
import com.example.addison_assessment.service.ISimpleAsyncTokenService ;

@RestController
@RequestMapping("/api/v1/tokens")
public class TokenController {
    private final ISimpleAsyncTokenService tokenService;

    public TokenController(ISimpleAsyncTokenService tokenService) {
        this.tokenService = tokenService;
    }

    @PostMapping
    public CompletableFuture<ResponseEntity<UserToken>> issueToken(
            @RequestBody Credentials credentials) {

            return tokenService.issueToken(credentials)
                .thenApply(ResponseEntity::ok);
            }

} 
