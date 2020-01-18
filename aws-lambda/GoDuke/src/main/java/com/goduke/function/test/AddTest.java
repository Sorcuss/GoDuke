package com.goduke.function.test;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.goduke.function.translate.QuestionTranslator;
import com.goduke.model.*;
import com.goduke.validator.TestValidator;


public class AddTest implements RequestHandler<Test, Test> {
    DynamoDBMapper dynamoDBMapper = new DynamoDBMapper(AmazonDynamoDBClientBuilder.defaultClient());

    @Override
    public Test handleRequest(Test input, Context context) {
        if(input.getLanguages().size() == 2){
            QuestionTranslator questionTranslator = new QuestionTranslator(input);
            questionTranslator.translate();
        }
        if(!TestValidator.validate(input)){
            throw new RuntimeException("test has invalid data");
        }
        dynamoDBMapper.save(input);
        return input;
    }
}
