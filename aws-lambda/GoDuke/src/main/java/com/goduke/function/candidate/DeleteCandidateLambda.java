package com.goduke.function.candidate;

import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProviderClientBuilder;
import com.amazonaws.services.cognitoidp.model.AdminDeleteUserRequest;
import com.amazonaws.services.cognitoidp.model.AdminDeleteUserResult;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.goduke.cognito.AmazonCognitoConnector;
import com.goduke.model.User;

public class DeleteCandidateLambda  implements RequestHandler<User, String>{

    @Override
    public String handleRequest(User user, Context context) {
        AWSCognitoIdentityProvider cognitoIdentityProvider = AWSCognitoIdentityProviderClientBuilder.defaultClient();
        AdminDeleteUserRequest adminDeleteUserRequest = new AdminDeleteUserRequest()
                .withUserPoolId(AmazonCognitoConnector.USER_POOL_ID)
                .withUsername(user.getMail());
        AdminDeleteUserResult adminDeleteUserResult = cognitoIdentityProvider.adminDeleteUser(adminDeleteUserRequest);
        return adminDeleteUserResult.toString();
    }
}
