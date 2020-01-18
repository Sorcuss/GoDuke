package com.goduke.function.answer;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.goduke.model.answer.Answer;

public class DeleteAnswerHandler implements RequestHandler<Answer, String> {
    private DynamoDBMapper dynamoDBMapper = new DynamoDBMapper(AmazonDynamoDBClientBuilder.defaultClient());
    @Override
    public String handleRequest(Answer input, Context context) {
        if(input.getId() == null){
           throw new RuntimeException("null id");
        }
        Answer answer = dynamoDBMapper.load(Answer.class, input.getId());
        if(answer == null){
          throw new RuntimeException("answer does not exist");
        }
        dynamoDBMapper.delete(answer);
        answer = dynamoDBMapper.load(Answer.class, input.getId());
        if(answer == null){
            return "success";
        }
        throw new RuntimeException("error during delete");
    }
}
