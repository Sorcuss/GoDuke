package com.goduke.function.question;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.goduke.model.Question;
import com.goduke.model.Test;


import java.util.ArrayList;
import java.util.List;

public class GetAllQuestionsHandler implements RequestHandler<Question, List<Question>> {
    private DynamoDBMapper mapper = new DynamoDBMapper(AmazonDynamoDBClientBuilder.defaultClient());
    @Override
    public List<Question> handleRequest(Question input, Context context) {
        List<Test> tests = mapper.scan(Test.class, new DynamoDBScanExpression());
        List<Question> questions = new ArrayList<>();
        for(Test test : tests){
            questions.addAll(test.getQuestions());
        }
        return questions;
    }
}
