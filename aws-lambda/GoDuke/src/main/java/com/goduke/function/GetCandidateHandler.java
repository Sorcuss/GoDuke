package com.goduke.functions;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.goduke.model.Candidate;

public class GetCandidateHandler implements RequestHandler<Candidate, Candidate> {
    @Override
    public Candidate handleRequest(Candidate candidateRequest, Context context) {
        // Create a connection to DynamoDB
        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.defaultClient();

        // Build a mapper
        DynamoDBMapper mapper = new DynamoDBMapper(client);

        // Load the candidate by ID
        Candidate candidate = mapper.load(Candidate.class, candidateRequest.getId());
        if(candidate == null) {
            // We did not find a widget with this ID, so return an empty Widget
            context.getLogger().log("Error! No Candidate found with ID: " + candidateRequest.getId() + "\n");
            return null;
        }
        context.getLogger().log("Success!");
        // Return  candidate
        return candidate;
    }
}