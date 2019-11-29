package com.goduke.function.recruiter;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.goduke.model.Recruiter;
import com.goduke.validator.RecruiterValidator;

public class AddRecruiterHandler implements RequestHandler<Recruiter, String> {
    DynamoDBMapper dynamoDBMapper = new DynamoDBMapper(AmazonDynamoDBClientBuilder.defaultClient());
    @Override
    public String handleRequest(Recruiter recruiter, Context context) {
        if(!RecruiterValidator.validate(recruiter)){
            return "Error";
        }
        Recruiter recruiterToAdd = new Recruiter(recruiter);
        dynamoDBMapper.save(recruiterToAdd);
        return "Success!: " + recruiterToAdd.getId() + " added.";
    }
}
