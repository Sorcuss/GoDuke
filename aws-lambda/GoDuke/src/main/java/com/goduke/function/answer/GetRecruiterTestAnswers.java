package com.goduke.function.answer;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.goduke.model.Answer;
import com.goduke.model.Recruiter;

import java.sql.ClientInfoStatus;
import java.util.List;
import java.util.stream.Collectors;

public class GetRecruiterTestAnswers implements RequestHandler<Recruiter, List<Answer>> {
    private DynamoDBMapper dynamoDBMapper = new DynamoDBMapper(AmazonDynamoDBClientBuilder.defaultClient());
    @Override
    public List<Answer> handleRequest(Recruiter input, Context context) {
        List<Answer> answers =  dynamoDBMapper.scan(Answer.class, new DynamoDBScanExpression());
        if(answers != null && input.getId() != null){
            return answers.stream()
                    .filter(answer -> answer.getTest().getRecruiter().equals(input.getId()))
                    .collect(Collectors.toList());
        }
        return null;
    }
}
