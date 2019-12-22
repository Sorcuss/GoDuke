package com.goduke.function.question;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.goduke.model.Question;
import com.goduke.model.QuestionHelper;
import com.goduke.model.Test;

public class GetQuestionFromTestHandler implements RequestHandler<QuestionHelper, Question> {
    private DynamoDBMapper dynamoDBMapper = new DynamoDBMapper(AmazonDynamoDBClientBuilder.defaultClient());
    @Override
    public Question handleRequest(QuestionHelper input, Context context) {
        if(input.getTestId() == null || input.getQuestionId() == null){
            throw new RuntimeException("null id");
        }
        Test test = dynamoDBMapper.load(Test.class, input.getTestId());
        if(test == null){
            throw new RuntimeException("test does not exist");
        }
        for(Question question : test.getQuestions()){
            if(question.getId().equals(input.getQuestionId())){
                return question;
            }
        }
        throw new RuntimeException("question does not exist");
    }
}
