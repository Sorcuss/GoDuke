package com.goduke.function.candidate;

import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProviderClientBuilder;
import com.amazonaws.services.cognitoidp.model.*;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.goduke.cognito.AmazonCognitoConnector;
import com.goduke.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class GetAllCandidatesHandler implements RequestHandler<Object, List<User>> {

    @Override
    public List<User> handleRequest(Object size, Context context) {
        AWSCognitoIdentityProvider cognitoIdentityProvider = AWSCognitoIdentityProviderClientBuilder.defaultClient();
        List<User> users = new ArrayList<>();
        ListUsersInGroupResult usersResult = cognitoIdentityProvider.listUsersInGroup(new ListUsersInGroupRequest().withGroupName("candidates").withUserPoolId(AmazonCognitoConnector.USER_POOL_ID));
        users.addAll(usersResult.getUsers().stream().map(u -> createUser(u)).collect(Collectors.toList()));
        return users;
    }

    private User createUser(UserType type){
        String mail = "";
        Boolean verified = Boolean.FALSE;
        for(AttributeType attributeType : type.getAttributes()){
            if(attributeType.getName().equals("email_verified")){
                verified = Boolean.getBoolean(attributeType.getValue());
            }else if(attributeType.getName().equals("email")){
                mail = attributeType.getValue();
            }
        }
        return new User(mail, verified);
    }
}
