package com.goduke.function.test;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.goduke.model.Test;
import com.google.gson.Gson;

public class GetTest implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {
    DynamoDBMapper dynamoDBMapper = new DynamoDBMapper(AmazonDynamoDBClientBuilder.defaultClient());
    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent input, Context context) {
        String id = input.getPathParameters().get("id");
        if(id == null){
            return new APIGatewayProxyResponseEvent().withStatusCode(400).withBody("error");
        }
        Test test = dynamoDBMapper.load(Test.class, id);
        if(test == null){
            return new APIGatewayProxyResponseEvent().withStatusCode(400).withBody("test with this id does not exist");
        }
        Gson gson = new Gson();
        return new APIGatewayProxyResponseEvent().withStatusCode(200).withBody(gson.toJson(test));
    }
}
