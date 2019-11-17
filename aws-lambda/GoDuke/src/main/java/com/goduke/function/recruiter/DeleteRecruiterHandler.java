package com.goduke.function.recruiter;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.goduke.model.Recruiter;

public class DeleteRecruiterHandler implements RequestHandler<Recruiter, String>  {
    DynamoDBMapper dynamoDBMapper = new DynamoDBMapper(AmazonDynamoDBClientBuilder.defaultClient());
    @Override
    public String handleRequest(Recruiter recruiter, Context context) {
        Recruiter recruiterToDelete = dynamoDBMapper.load(Recruiter.class, recruiter.getId());
        if(recruiterToDelete == null) {
            throw new RuntimeException("Error!: object does not exist.");
        }
        dynamoDBMapper.delete(recruiterToDelete);
        Recruiter deletedCandidate = dynamoDBMapper.load(Recruiter.class, recruiter.getId());
        if (deletedCandidate == null) {
            return "Success!" + recruiter.getId() + " deleted.";
        }
        throw new RuntimeException("Error!: cant execute operation.");
    }
}
