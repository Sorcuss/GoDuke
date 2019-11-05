package com.goduke.function;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.goduke.model.Candidate;

public class GetCandidateByLoginHandler implements RequestHandler<Candidate, Candidate> {
    @Override
    public Candidate handleRequest(Candidate candidateRequest, Context context) {
        // Create a connection to DynamoDB
        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.defaultClient();

        // Build a mapper
        DynamoDBMapper mapper = new DynamoDBMapper(client);

        // Load the candidate by email and password
        Candidate candidate = mapper.load(Candidate.class, candidateRequest.getEmail(), candidateRequest.getPassword());
        if(candidate == null) {
            context.getLogger().log("Error! Email or password incorrect\n");
            return null;
        }
        context.getLogger().log("Success!");
        // Return  candidate
        return candidate;
    }
}