package com.goduke.function.candidate;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.goduke.model.Candidate;
import com.google.gson.Gson;

public class GetCandidateHandler implements RequestHandler<Candidate, Candidate> {
    DynamoDBMapper dynamoDBMapper = new DynamoDBMapper(AmazonDynamoDBClientBuilder.defaultClient());
    @Override
    public Candidate handleRequest(Candidate item, Context context) {
        String id = item.getId();
        if(id == null){
            throw new RuntimeException("null id");
        }
        Candidate candidateToGet = dynamoDBMapper.load(Candidate.class, id);
        if(candidateToGet == null){
            throw new RuntimeException("candidate does not exist");
        }
        return candidateToGet;
    }
}