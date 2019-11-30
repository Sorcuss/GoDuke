package com.goduke.function.candidate;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.goduke.model.Candidate;
import com.google.gson.Gson;

public class GetCandidateHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {
    DynamoDBMapper dynamoDBMapper = new DynamoDBMapper(AmazonDynamoDBClientBuilder.defaultClient());
    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent item, Context context) {
        String id = item.getPathParameters().get("id");
        if(id == null){
            return new APIGatewayProxyResponseEvent().withStatusCode(400).withBody("id error");
        }
        Candidate candidate = dynamoDBMapper.load(Candidate.class, id);
        if(candidate == null) {
            return new APIGatewayProxyResponseEvent().withStatusCode(400).withBody("Candidate does not exist");
        }
        Gson gson = new Gson();
        return new APIGatewayProxyResponseEvent().withStatusCode(200).withBody(gson.toJson(candidate));
    }
}