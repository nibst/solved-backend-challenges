package com.example.addison_assessment.service.impl;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.stereotype.Service;

import com.example.addison_assessment.exceptions.AuthException;
import com.example.addison_assessment.model.Credentials;
import com.example.addison_assessment.model.User;

@Service
public class AuthService{
    public CompletableFuture<User> authenticate(Credentials credentials) throws AuthException{
        // validate credentials: password should match username in upercase
        // Ex: user: house, password: HOUSE => Valid
        return CompletableFuture.supplyAsync(() -> {
            String username = credentials.getUsername();
            String password = credentials.getPassword();
            if (username.toUpperCase().equals(password)){
                randomDelay(0, 5000);
                return new User(credentials.getUsername());
            }
            throw new AuthException("Invalid Credentials");
        });
    }
    private void randomDelay(int minMs, int maxMs){
        try {
            int delay = ThreadLocalRandom.current().nextInt(minMs, maxMs);
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
