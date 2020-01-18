package com.goduke.function.answer;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.goduke.model.Answer;
import com.goduke.validator.AnswerValidator;

public class AddAnswerHandler implements RequestHandler<Answer, String> {
    private DynamoDBMapper dynamoDBMapper = new DynamoDBMapper(AmazonDynamoDBClientBuilder.defaultClient());

    @Override
    public String handleRequest(Answer input, Context context) {
        String id = input.getId();
        if(id != null){
            throw new RuntimeException("error id");
        }
        if(!AnswerValidator.validate(input)){
            throw new RuntimeException("answers has invalid data");
        }
        dynamoDBMapper.save(input);
        return "Success";
    }
}
