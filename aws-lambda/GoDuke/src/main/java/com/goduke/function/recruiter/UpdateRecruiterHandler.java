package com.goduke.function.recruiter;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.lambda.runtime.Client;
import com.amazonaws.services.lambda.runtime.ClientContext;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.goduke.model.Recruiter;
import com.goduke.validator.RecruiterValidator;

public class UpdateRecruiterHandler implements RequestHandler<Recruiter, String> {
    private DynamoDBMapper dynamoDBMapper = new DynamoDBMapper(AmazonDynamoDBClientBuilder.defaultClient());
    @Override
    public String handleRequest(Recruiter recruiter, Context context) {
        if(!RecruiterValidator.validate(recruiter)){
            throw new RuntimeException("recruiter have invalid data");
        }
        Recruiter recruiterToUpdate = dynamoDBMapper.load(Recruiter.class, recruiter.getId());
        if(recruiterToUpdate == null){
            throw new RuntimeException("Error!: " + recruiter.getId() + " does not exist.");
        }
        dynamoDBMapper.save(recruiter);
        return "Success!: " + recruiter.getId() + " updated.";
    }
}
