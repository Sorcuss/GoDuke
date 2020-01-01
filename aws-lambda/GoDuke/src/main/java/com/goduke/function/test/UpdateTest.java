package com.goduke.function.test;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.goduke.model.Test;
import com.goduke.validator.TestValidator;


public class UpdateTest implements RequestHandler<Test, String> {
    DynamoDBMapper dynamoDBMapper = new DynamoDBMapper(AmazonDynamoDBClientBuilder.defaultClient());
    @Override
    public String handleRequest(Test input, Context context) {
        if(!TestValidator.validate(input)){
            throw new RuntimeException("test have invalid data");
        }
        Test testToUpdate = dynamoDBMapper.load(Test.class, input.getId());
        if(testToUpdate == null){
            throw new RuntimeException("test does not exist");
        }
        dynamoDBMapper.save(input);
        return "Success";
    }
}
