package com.goduke.function;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.goduke.model.Candidate;

public class AddNewCandidateHandler implements RequestHandler<Candidate, String> {
    @Override
    public String handleRequest(Candidate candidateRequest, Context context) {
        // Create a connection to DynamoDB
        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.defaultClient();

        // Build a mapper
        DynamoDBMapper mapper = new DynamoDBMapper(client);

        //save candidate
        mapper.save(candidateRequest);

        return "Success!";
    }
}