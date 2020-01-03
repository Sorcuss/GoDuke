package com.goduke.function.candidate;

import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProviderClientBuilder;
import com.amazonaws.services.cognitoidp.model.*;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.goduke.cognito.AmazonCognitoConnector;
import com.goduke.model.Candidate;
import com.goduke.model.User;
import com.goduke.validator.CandidateValidator;

public class AddCandidateHandler implements RequestHandler<User, String> {
    @Override
    public String handleRequest(User user, Context context) {
        AWSCognitoIdentityProvider cognitoIdentityProvider = AWSCognitoIdentityProviderClientBuilder.defaultClient();
        AdminCreateUserRequest cognitoRequest = new AdminCreateUserRequest()
                .withUserPoolId(AmazonCognitoConnector.USER_POOL_ID)
                .withUsername(user.getMail())
                .withUserAttributes(
                    new AttributeType()
                        .withName("email")
                        .withValue(user.getMail()));

        AdminCreateUserResult createUserResult =  cognitoIdentityProvider.adminCreateUser(cognitoRequest);
        AdminAddUserToGroupRequest adminAddUserToGroupRequest = new AdminAddUserToGroupRequest()
                .withGroupName("candidates")
                .withUserPoolId(AmazonCognitoConnector.USER_POOL_ID)
                .withUsername(user.getMail());

        cognitoIdentityProvider.adminAddUserToGroup(adminAddUserToGroupRequest);

        cognitoIdentityProvider.shutdown();


        return createUserResult.toString();
    }
}