package com.goduke.function.recruiter;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.goduke.model.Recruiter;
import com.goduke.util.CheckUnique;

public class UpdateRecruiterHandler implements RequestHandler<Recruiter, String> {
    private DynamoDBMapper dynamoDBMapper = new DynamoDBMapper(AmazonDynamoDBClientBuilder.defaultClient());
    @Override
    public String handleRequest(Recruiter recruiter, Context context) {
        if(CheckUnique.checkRecruiterEmail(dynamoDBMapper, recruiter.getEmail()) == false){
            throw new RuntimeException("Error!: wrong email.");
        }
        Recruiter recruiterToUpdate = dynamoDBMapper.load(Recruiter.class, recruiter.getId());
        if(recruiterToUpdate == null){
            throw new RuntimeException("Error!: " + recruiter.getId() + " does not exist.");
        }
        dynamoDBMapper.save(recruiter);
        return "Success!: " + recruiter.getId() + " updated.";
    }
}
