package com.goduke.function.answer;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.goduke.model.answer.Answer;

import java.util.List;

public class GetAnswersHandler implements RequestHandler<Answer, List<Answer>> {
    private DynamoDBMapper dynamoDBMapper = new DynamoDBMapper(AmazonDynamoDBClientBuilder.defaultClient());
    @Override
    public List<Answer> handleRequest(Answer input, Context context) {
        return dynamoDBMapper.scan(Answer.class, new DynamoDBScanExpression());
    }
}
