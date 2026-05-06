package com.example.addison_assessment.service.impl;

import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.addison_assessment.service.ISimpleAsyncTokenService;
import com.example.addison_assessment.service.impl.TokenGenerationService;
import com.example.addison_assessment.model.*;

@Service
public class SimpleAsyncTokenService implements ISimpleAsyncTokenService{
    private final AuthService authService;
    private final TokenGenerationService tokenGenerationService;
    @Autowired
    public SimpleAsyncTokenService(AuthService authService, TokenGenerationService tokenGenerationService){
        this.authService = authService;
        this.tokenGenerationService = tokenGenerationService;
    }
    public CompletableFuture<UserToken> issueToken(Credentials credentials) {
       return authService.authenticate(credentials)
           .thenCompose(user -> tokenGenerationService.generateToken(user));
    }

}
