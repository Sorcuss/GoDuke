package com.goduke.function.question;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.goduke.model.Question;
import com.goduke.validator.QuestionValidator;

import java.util.Map;

public class UpdateQuestion implements RequestHandler<Question, String> {
	DynamoDBMapper dynamoDBMapper = new DynamoDBMapper(AmazonDynamoDBClientBuilder.defaultClient());

	@Override
	public String handleRequest(Question questionRequest, Context context) {
		if(!QuestionValidator.validate(questionRequest)){
			return "Error";
		}
		Question question = dynamoDBMapper.load(Question.class, questionRequest.getId());
		if(question == null){
			return "Error! question with this id does not exist";
		}
		dynamoDBMapper.save(questionRequest);
		return "Success";
	}
}