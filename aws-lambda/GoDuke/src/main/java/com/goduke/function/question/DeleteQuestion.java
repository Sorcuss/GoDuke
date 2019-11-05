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

public class DeleteQuestion implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

	@Override
	public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent request, Context context) {
		Map<String, String> pathParams = request.getPathParameters();
		if (pathParams.get("id") == null || pathParams.get("id").isEmpty()) {
			return new APIGatewayProxyResponseEvent().withBody("Error!");
		}
		AmazonDynamoDB client = AmazonDynamoDBClientBuilder.defaultClient();
		DynamoDBMapper mapper = new DynamoDBMapper(client);
		Question question = mapper.load(Question.class, Integer.parseInt(pathParams.get("id")));
		if (question == null) {
			return new APIGatewayProxyResponseEvent().withBody("Error!");
		}
		 return deleteItem(question, mapper);
	}

	private APIGatewayProxyResponseEvent deleteItem(Question question, DynamoDBMapper mapper) {
		mapper.delete(question);
		Question deletedQuestion = mapper.load(Question.class, question.getNumber());
		if (deletedQuestion == null) {
			return new APIGatewayProxyResponseEvent().withBody("Succes!");
		}
		return new APIGatewayProxyResponseEvent().withBody("Error!");
	}
}
