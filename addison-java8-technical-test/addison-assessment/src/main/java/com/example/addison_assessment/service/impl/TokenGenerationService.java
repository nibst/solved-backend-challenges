package com.example.addison_assessment.service.impl;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadLocalRandom;

import com.example.addison_assessment.model.*;
import com.example.addison_assessment.exceptions.UserTokenException;

import org.springframework.stereotype.Service;

@Service
public class TokenGenerationService{
    public CompletableFuture<UserToken> generateToken(User user){
        return CompletableFuture.supplyAsync(() -> {
            if (!user.getUserId().startsWith("A")){
                String now = LocalDateTime.now(ZoneOffset.UTC).format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'"));
                randomDelay(0,5000);
                return new UserToken(user.getUserId() + now);
            }
            throw new UserTokenException("Token cannot be granted for userId starting with 'A': " + user.getUserId());
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
