package com.goduke.function;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.goduke.model.Recruiter;

public class RecruiterLambda implements RequestHandler<Recruiter, String> {
	DynamoDBMapper dynamoDBMapper = new DynamoDBMapper(AmazonDynamoDBClientBuilder.defaultClient());
	public String handleRequest(Recruiter request, Context context) {
		dynamoDBMapper.save(new Recruiter(request));
		return "Success!";
	}
	
	public Recruiter handleGetByParam(Recruiter recruiter) {
		return dynamoDBMapper.load(Recruiter.class, recruiter.getId());
	}
}
