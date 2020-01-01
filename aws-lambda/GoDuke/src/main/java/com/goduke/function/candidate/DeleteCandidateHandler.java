package com.goduke.function.candidate;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.goduke.model.Candidate;

public class DeleteCandidateHandler  implements RequestHandler<Candidate, String>{
    DynamoDBMapper dynamoDBMapper = new DynamoDBMapper(AmazonDynamoDBClientBuilder.defaultClient());

    @Override
    public String handleRequest(Candidate candidateRequest, Context context) {
        if(candidateRequest.getId() == null){
            throw new RuntimeException("error null id");
        }
        Candidate candidateToDelete = dynamoDBMapper.load(Candidate.class, candidateRequest.getId());

        Candidate candidate = dynamoDBMapper.load(Candidate.class, candidateRequest.getId());
        if(candidate == null) {
            throw new RuntimeException("error candidate does not exist");
        }

        dynamoDBMapper.delete(candidate);
        Candidate deletedCandidate = dynamoDBMapper.load(Candidate.class, candidateRequest.getId());
        if (deletedCandidate == null) {
            return "Success!";
        }
        throw new RuntimeException("error during delete");
    }
}
