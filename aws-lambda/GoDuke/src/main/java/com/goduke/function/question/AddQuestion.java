package com.goduke.function.question;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.goduke.model.Question;
import com.goduke.validator.QuestionValidator;

@Deprecated
public class AddQuestion implements RequestHandler<Question, String> {
    DynamoDBMapper dynamoDBMapper = new DynamoDBMapper(AmazonDynamoDBClientBuilder.defaultClient());

    @Override
    public String handleRequest(Question question, Context context) {
        if(!QuestionValidator.validate(question)){
            return "Error!";
        }
        dynamoDBMapper.save(question);
        return "Success";
    }
}
