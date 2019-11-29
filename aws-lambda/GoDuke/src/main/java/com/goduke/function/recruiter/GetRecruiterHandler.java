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

public class GetRecruiterHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {
    private DynamoDBMapper dynamoDBMapper = new DynamoDBMapper(AmazonDynamoDBClientBuilder.defaultClient());
    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent input, Context context){
        String id = input.getPathParameters().get("id");
        if(id == null){
            return new APIGatewayProxyResponseEvent().withStatusCode(400).withBody("null id");
        }
        Recruiter recruiterToGet = dynamoDBMapper.load(Recruiter.class, id);
        if(recruiterToGet == null){
            return new APIGatewayProxyResponseEvent().withStatusCode(400).withBody("recruiter does not exist");
        }
        Gson gson = new Gson();
        return new APIGatewayProxyResponseEvent().withStatusCode(200).withBody(gson.toJson(recruiterToGet));
    }
}
