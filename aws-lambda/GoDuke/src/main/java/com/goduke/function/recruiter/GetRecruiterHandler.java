package com.goduke.function.recruiter;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.goduke.model.Recruiter;

public class GetRecruiterHandler implements RequestHandler<Recruiter, Recruiter> {
    private DynamoDBMapper dynamoDBMapper = new DynamoDBMapper(AmazonDynamoDBClientBuilder.defaultClient());
    @Override
    public Recruiter handleRequest(Recruiter recruiter, Context context){
        Recruiter recruiterToGet = dynamoDBMapper.load(Recruiter.class, recruiter.getId());
        if(recruiterToGet == null){
            throw new RuntimeException("Error!: object does not exist.");
        }
        return recruiterToGet;
    }
}
