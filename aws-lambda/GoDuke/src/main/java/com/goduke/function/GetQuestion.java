package com.goduke.function;

import java.util.Map;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.goduke.model.Question;
import com.google.gson.Gson;

public class GetQuestion implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent request, Context context) {
		 Map<String, String> params = request.getPathParameters();
		 if(params.get("id") == null || params.get("id").isEmpty()) {
			 return new APIGatewayProxyResponseEvent().withStatusCode(400).withBody("error");
		 }
		 
		 
		 else {
			 AmazonDynamoDB client = AmazonDynamoDBClientBuilder.defaultClient();
		     DynamoDBMapper mapper = new DynamoDBMapper(client);
		     Question quest = mapper.load(Question.class, Integer.parseInt(params.get("id")));
		     if(quest == null) {
			 return new APIGatewayProxyResponseEvent().withStatusCode(400).withBody(null);
		     }
		     return new APIGatewayProxyResponseEvent().withStatusCode(200).withBody(quest.toString());
		 }
	 }

}
