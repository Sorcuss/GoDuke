package com.goduke.function.test;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.goduke.function.translate.QuestionTranslator;
import com.goduke.model.Test;
import com.goduke.validator.TestValidator;


public class UpdateTest implements RequestHandler<Test, Test> {
    DynamoDBMapper dynamoDBMapper = new DynamoDBMapper(AmazonDynamoDBClientBuilder.defaultClient());
    @Override
    public Test handleRequest(Test input, Context context) {
        Test testToUpdate = dynamoDBMapper.load(Test.class, input.getId());
        if(testToUpdate == null){
            throw new RuntimeException("test does not exist");
        }
        if(testToUpdate.getLanguages().size() == 1) {
            if (input.getLanguages().size() == 2 && !TestValidator.questionNumberValidation(input)) {
                QuestionTranslator questionTranslator = new QuestionTranslator(input);
                questionTranslator.translate();
            }
        }
        if(!TestValidator.validate(input)){
            throw new RuntimeException("test has invalid data");
        }
        dynamoDBMapper.save(input);
        return input;
    }
}
