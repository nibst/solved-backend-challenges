package com.example.addison_assessment.service;

import com.example.addison_assessment.model.Credentials;
import com.example.addison_assessment.model.User;
import com.example.addison_assessment.model.UserToken;

public interface ISyncTokenService {

    User authenticate(Credentials credentials);

    UserToken requestToken(User user);

    default UserToken issueToken(Credentials credentials) {
        // Validate credentials
        User user = authenticate(credentials);
        // Get token
        UserToken token = requestToken(user);
        return token;
    }

}
