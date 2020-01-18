package com.goduke.function.answer;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.goduke.model.answer.Answer;
import com.goduke.model.test.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

;

public class GetRecruiterTestAnswers implements RequestHandler<String, List<Answer>> {
    private DynamoDBMapper dynamoDBMapper = new DynamoDBMapper(AmazonDynamoDBClientBuilder.defaultClient());
    @Override
    public List<Answer> handleRequest(String input, Context context) {
        List<Answer> answers =  dynamoDBMapper.scan(Answer.class, new DynamoDBScanExpression());
        List<Test> tests = getRecruiterTest(input);
        List<Answer> result = new ArrayList<>();
        for(Answer answer :answers){
            for(Test test : tests){
                if(answer.getTest().getId().equals(test.getId()))
                    result.add(answer);
            }
        }
       return result;
    }

    private List<Test> getRecruiterTest(String email){
        return dynamoDBMapper.scan(Test.class, new DynamoDBScanExpression())
                .stream()
                .filter(test -> test.getRecruiter().equals(email))
                .collect(Collectors.toList());
    }
}
