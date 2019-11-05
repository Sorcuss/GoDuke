package com.goduke.function;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.goduke.model.Candidate;

public class DeleteCandidateHandler  implements RequestHandler<Candidate, String>{
    @Override
    public String handleRequest(Candidate candidateRequest, Context context) {
        // Create a connection to DynamoDB
        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.defaultClient();

        // Build a mapper
        DynamoDBMapper mapper = new DynamoDBMapper(client);

        // Load the candidate by ID
        Candidate candidate = mapper.load(Candidate.class, candidateRequest.getId());
        if(candidate == null) {
            context.getLogger().log("Error!");
            return "Error!";
        }

        mapper.delete(candidate);
        Candidate deletedCandidate = mapper.load(Candidate.class, candidateRequest.getId());
        if (deletedCandidate == null) {
            context.getLogger().log("Success!");
            return "Success!";
        }

        return "Error!";
    }
}
