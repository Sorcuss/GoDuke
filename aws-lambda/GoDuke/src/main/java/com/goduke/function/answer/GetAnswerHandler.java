package com.goduke.function.answer;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.goduke.model.answer.Answer;

public class GetAnswerHandler implements RequestHandler<Answer, Answer> {
    private DynamoDBMapper dynamoDBMapper = new DynamoDBMapper(AmazonDynamoDBClientBuilder.defaultClient());
    @Override
    public Answer handleRequest(Answer input, Context context) {
        String id = input.getId();
        if(id == null){
            throw new RuntimeException("null id");
        }
        Answer answer = dynamoDBMapper.load(Answer.class, id);
        if(answer == null){
            throw new RuntimeException("answer does not exist");
        }
        return answer;
    }
}
