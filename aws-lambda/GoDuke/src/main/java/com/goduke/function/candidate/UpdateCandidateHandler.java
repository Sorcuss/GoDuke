package com.goduke.function.candidate;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.goduke.model.Candidate;
import com.goduke.validator.CandidateValidator;

public class UpdateCandidateHandler implements RequestHandler<Candidate, String> {
    DynamoDBMapper dynamoDBMapper = new DynamoDBMapper(AmazonDynamoDBClientBuilder.defaultClient());


    @Override
    public String handleRequest(Candidate candidateRequest, Context context) {
        if(!CandidateValidator.validate(candidateRequest)){
            return "Error";
        }
        Candidate candidate = dynamoDBMapper.load(Candidate.class, candidateRequest.getId());
        if(candidate == null){
            return "candidate with " + candidateRequest.getId() + " does not exist";
        }
        dynamoDBMapper.save(candidateRequest);
        return "Success";
    }
}