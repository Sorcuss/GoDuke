package com.goduke.function.question;

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
		Map<String, String> pathParams = request.getPathParameters();
		if (pathParams.get("id") == null || pathParams.get("id").isEmpty()) {
			return new APIGatewayProxyResponseEvent().withBody(null);
		}
		AmazonDynamoDB client = AmazonDynamoDBClientBuilder.defaultClient();
		DynamoDBMapper mapper = new DynamoDBMapper(client);
		Question question = mapper.load(Question.class, Integer.parseInt(pathParams.get("id")));
		if (question == null) {
			return new APIGatewayProxyResponseEvent().withBody(null);
		}
		return new APIGatewayProxyResponseEvent().withBody(question.toString());
	}

}
