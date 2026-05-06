package com.example.addison_assessment.service;

import java.util.concurrent.CompletableFuture;

import com.example.addison_assessment.model.Credentials;
import com.example.addison_assessment.model.UserToken;

public interface ISimpleAsyncTokenService {

  default CompletableFuture<UserToken> issueToken(Credentials credentials) {
      throw new UnsupportedOperationException("Not Implemented"); //TODO: Implement this
  }
}
