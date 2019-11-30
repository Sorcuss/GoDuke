package com.goduke.function.test;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.xspec.L;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.goduke.converter.CandidatesTypeConverter;
import com.goduke.model.*;
import com.goduke.validator.TestValidator;
import org.w3c.dom.css.Rect;

import java.util.ArrayList;
import java.util.List;

public class AddTest implements RequestHandler<TestHelper, String> {
    DynamoDBMapper dynamoDBMapper = new DynamoDBMapper(AmazonDynamoDBClientBuilder.defaultClient());

    @Override
    public String handleRequest(TestHelper input, Context context) {
        Test test = new Test(input.getId(),
                input.getLanguages(),
                input.getName(),
                readQuestions(input.getQuestionsIds()),
                readRecruiter(input.getRecruiterId()),
                readCandidates(input.getCandidatesIds()));
        if(!TestValidator.validate(test)){
            return "Error";
        }
        dynamoDBMapper.save(test);
        return "Success";
    }

    private List<Question> readQuestions(List<String> questionsIds){
        List<Question> questions = new ArrayList<>();
        for(String row : questionsIds){
            questions.add(dynamoDBMapper.load(Question.class, row));
        }
        return questions;
    }

    private List<Candidate> readCandidates(List<String> candidateIds){
        List<Candidate> candidates = new ArrayList<>();
        if(candidateIds.size() != 0) {

            for (String row : candidateIds) {
                candidates.add(dynamoDBMapper.load(Candidate.class, row));
            }
            return candidates;
        }
        return candidates;
    }

    private Recruiter readRecruiter(String id){
        return dynamoDBMapper.load(Recruiter.class,id);
    }
}
