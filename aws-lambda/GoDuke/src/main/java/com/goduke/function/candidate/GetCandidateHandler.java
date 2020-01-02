package com.goduke.function.candidate;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.goduke.cognito.AmazonCognitoConnector;

import java.util.List;

public class GetCandidateHandler implements RequestHandler<String, List<String>> {
    @Override
    public List<String> handleRequest(String input, Context context) {
        return null;
    }
//    AmazonCognitoConnector amazonCognitoConnector = new AmazonCognitoConnector();
//    @Override
//    public List<String> handleRequest(String count, Context context) {
//        System.out.println("hello");
//        return amazonCognitoConnector.listUsers(60);
//    }
}