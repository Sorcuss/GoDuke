package com.goduke.function.question;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.goduke.model.Question;

public class DeleteQuestion implements RequestHandler<Question, String> {
	DynamoDBMapper dynamoDBMapper = new DynamoDBMapper(AmazonDynamoDBClientBuilder.defaultClient());

	@Override
	public String handleRequest(Question question, Context context) {
		Question questionToDelete = dynamoDBMapper.load(Question.class, question.getId());
		if(questionToDelete == null){
			return "Error! question with this id does not exit";
		}
		dynamoDBMapper.delete(questionToDelete);
		Question deletedQuestion = dynamoDBMapper.load(Question.class, question.getId());
		if(deletedQuestion == null){
			return "Success";
		}
		return "Error";
	}
}
