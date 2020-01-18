package com.goduke.function.answer;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.goduke.model.answer.Answer;
import com.goduke.model.validator.AnswerValidator;

public class UpdateAnswerHandler implements RequestHandler<Answer, String> {
    private DynamoDBMapper dynamoDBMapper = new DynamoDBMapper(AmazonDynamoDBClientBuilder.defaultClient());

    @Override
    public String handleRequest(Answer input, Context context) {
        if(input.getId() == null){
            throw new RuntimeException("error null id");
        }
        if(!AnswerValidator.validate(input)){
            throw new RuntimeException("answers has invalid data");
        }
        Answer answer = dynamoDBMapper.load(Answer.class, input.getId());
        if(answer == null){
            throw new RuntimeException("error answer does not exist");
        }
        dynamoDBMapper.save(input);
        return "Success";
    }
}
