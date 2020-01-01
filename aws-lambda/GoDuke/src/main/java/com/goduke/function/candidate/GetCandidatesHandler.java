package com.goduke.function.candidate;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.goduke.model.Candidate;
import com.goduke.model.Recruiter;

import java.util.List;

public class GetCandidatesHandler implements RequestHandler<Candidate, List<Candidate>> {
    DynamoDBMapper dynamoDBMapper = new DynamoDBMapper(AmazonDynamoDBClientBuilder.defaultClient());
    @Override
    public List<Candidate> handleRequest(Candidate input, Context context) {
        List<Candidate>  candidates = dynamoDBMapper.scan(Candidate.class, new DynamoDBScanExpression());
        return candidates;
    }
}
