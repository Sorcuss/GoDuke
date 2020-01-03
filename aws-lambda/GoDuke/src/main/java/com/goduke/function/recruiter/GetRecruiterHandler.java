package com.goduke.function.recruiter;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.goduke.model.Recruiter;
import com.google.gson.Gson;
import com.google.gson.internal.$Gson$Preconditions;

public class GetRecruiterHandler  {
    private DynamoDBMapper dynamoDBMapper = new DynamoDBMapper(AmazonDynamoDBClientBuilder.defaultClient());
    public Recruiter handleRequest(Recruiter recruiter, Context context){
        String id = recruiter.getId();
        if(id == null){
            throw new RuntimeException("null id");
        }
        Recruiter recruiterToGet = dynamoDBMapper.load(Recruiter.class, id);
        if(recruiterToGet == null){
            throw new RuntimeException("recruiter does not exist");
        }
        return recruiterToGet;
    }
}
