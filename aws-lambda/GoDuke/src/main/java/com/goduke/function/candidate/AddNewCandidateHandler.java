package com.goduke.function.candidate;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.goduke.model.Candidate;

public class AddNewCandidateHandler implements RequestHandler<Candidate, String> {
    DynamoDBMapper dynamoDBMapper = new DynamoDBMapper(AmazonDynamoDBClientBuilder.defaultClient());
    @Override
    public String handleRequest(Candidate candidateRequest, Context context) {


        //save candidate
        dynamoDBMapper.save(candidateRequest);

        return "Success!";
    }
}