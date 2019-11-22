package com.goduke.function.question;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.goduke.model.Question;

public class DeleteQuestion implements RequestHandler<Question, String> {

	@Override
	public String handleRequest(Question question, Context context) {
		// Create a connection to DynamoDB
		AmazonDynamoDB client = AmazonDynamoDBClientBuilder.defaultClient();

		// Build a mapper
		DynamoDBMapper mapper = new DynamoDBMapper(client);

		// Load the candidate by ID
		Question questionToDelete = mapper.load(Question.class, question.getId());
		if(questionToDelete == null) {
			return "Error!";
		}
		mapper.delete(questionToDelete);

		return "Success!";

	}
}
