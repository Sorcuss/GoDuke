package com.goduke.function.question;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.goduke.model.Question;

import java.util.Map;

public class UpdateQuestion implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

	@Override
	public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent request, Context context) {
		Map<String, String> pathParams = request.getPathParameters();
		if(request.getBody() == null || request.getBody().isEmpty() || pathParams.get("id") == null || pathParams.get("id").isEmpty()) {
			 return new APIGatewayProxyResponseEvent().withBody("Error!");
		}
		AmazonDynamoDB client = AmazonDynamoDBClientBuilder.defaultClient();
		DynamoDBMapper mapper = new DynamoDBMapper(client);
		Question question = mapper.load(Question.class, Integer.parseInt(pathParams.get("id")));
		Question updatedQuestion = new Question(request.getBody());
		if(question == null || !question.getId().equals(updatedQuestion.getId())) {
			 return new APIGatewayProxyResponseEvent().withBody("Error!");
	    }
		mapper.save(updatedQuestion);
		return new APIGatewayProxyResponseEvent().withBody("Success!");
	}
}