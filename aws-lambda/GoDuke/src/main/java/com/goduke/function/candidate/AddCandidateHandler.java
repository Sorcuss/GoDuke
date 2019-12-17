package com.goduke.function.candidate;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.goduke.model.Candidate;
import com.goduke.validator.CandidateValidator;

public class AddCandidateHandler implements RequestHandler<Candidate, String> {
    DynamoDBMapper dynamoDBMapper = new DynamoDBMapper(AmazonDynamoDBClientBuilder.defaultClient());
    @Override
    public String handleRequest(Candidate candidateRequest, Context context) {
        if(!CandidateValidator.validate(candidateRequest)){
            throw new RuntimeException("candidate have invalid data");
        }
        dynamoDBMapper.save(candidateRequest);
        return "Success!";
    }
}