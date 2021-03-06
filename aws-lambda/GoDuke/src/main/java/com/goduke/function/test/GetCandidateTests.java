package com.goduke.function.test;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.goduke.model.answer.Answer;
import com.goduke.model.test.CompletedTest;
import com.goduke.model.test.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class GetCandidateTests implements RequestHandler<String, List<TestWrapper>> {
    DynamoDBMapper dynamoDBMapper = new DynamoDBMapper(AmazonDynamoDBClientBuilder.defaultClient());

    @Override
    public List<TestWrapper> handleRequest(String input, Context context) {
        List<TestWrapper> result = new ArrayList<>();
        List<Test> candidatesTest = getCandidatesTest(input);
        List<Answer> candidateAnswers = getCandidateAnswers(input);
        for(Test test: candidatesTest){
            Answer closedTest = candidateAnswers.stream().filter(answer -> answer.getTest().getId().equals(test.getId())).findFirst().orElse(null);
            if(closedTest != null){
                if(closedTest.isRated()) {
                    result.add(new CompletedTest(closedTest.getTest().getTestName(), calculateScore(closedTest), closedTest.isRated(), closedTest.getAnswers().size()));
                }else{
                    result.add(new CompletedTest(closedTest.getTest().getTestName(), 0, false, closedTest.getAnswers().size()));
                }
            }
            else{
                result.add(test);
            }
        }
        return result;
    }

    private List<Test> getCandidatesTest(String id) {
        return dynamoDBMapper.scan(Test.class, new DynamoDBScanExpression()).stream()
                .filter(test -> {
                    List<String> candidates = test.getCandidates();
                    return candidates != null && candidates.contains(id);
                })
                .collect(Collectors.toList());
    }

    private List<Answer> getCandidateAnswers(String id){
        HashMap<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
        eav.put(":val1", new AttributeValue().withS(id));
        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression().withFilterExpression("candidate = :val1").withExpressionAttributeValues(eav);
        return dynamoDBMapper.scan(Answer.class, scanExpression);
    }

    private int calculateScore(Answer answer){
        int count = 0;
        for(boolean rate: answer.getRates()){
            if(rate) count++;
        }
        return count;
    }
}
