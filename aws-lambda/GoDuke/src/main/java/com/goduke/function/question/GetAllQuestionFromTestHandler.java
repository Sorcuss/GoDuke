package com.goduke.function.question;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.goduke.model.Question;
import com.goduke.model.QuestionHelper;
import com.goduke.model.Test;

import java.util.List;


public class GetAllQuestionFromTestHandler implements RequestHandler<QuestionHelper, List<Question>> {
    private DynamoDBMapper mapper = new DynamoDBMapper(AmazonDynamoDBClientBuilder.defaultClient());
    @Override
    public List<Question> handleRequest(QuestionHelper input, Context context) {
        if(input.getTestId() == null){
            throw new RuntimeException("null Test id");
        }
        Test test = mapper.load(Test.class, input.getTestId());
        if(test == null){
            throw new RuntimeException("Test does not exist");
        }
        return test.getQuestions();
    }
}
