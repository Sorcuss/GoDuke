package com.goduke.function.question;

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
	DynamoDBMapper dynamoDBMapper = new DynamoDBMapper(AmazonDynamoDBClientBuilder.defaultClient());

	@Override
	public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent input, Context context){
		String id = input.getPathParameters().get("id");
		if(id == null){
			return new APIGatewayProxyResponseEvent().withStatusCode(400).withBody("Error");
		}
		Question question = dynamoDBMapper.load(Question.class, id);
		if(question == null){
			return new APIGatewayProxyResponseEvent().withStatusCode(400).withBody("Question with this id does not exits");
		}
		Gson gson = new Gson();
		return new APIGatewayProxyResponseEvent().withStatusCode(200).withBody(gson.toJson(question));
	}
}
