package com.example.addison_assessment.service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import com.example.addison_assessment.model.Credentials;
import com.example.addison_assessment.model.User;
import com.example.addison_assessment.model.UserToken;
public interface IAsyncTokenService {

    CompletableFuture<User> authenticate(Credentials credentials);

    CompletableFuture<UserToken> requestToken(User user);

    default Future<UserToken> issueToken(Credentials credentials) {
        //Validate credentials and generate token
        return authenticate(credentials).thenCompose(user -> requestToken(user));
    }

}
