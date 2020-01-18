package com.goduke.function.test;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.goduke.model.test.Test;

public class DeleteTest implements RequestHandler<Test, String> {
    DynamoDBMapper dynamoDBMapper = new DynamoDBMapper(AmazonDynamoDBClientBuilder.defaultClient());

    @Override
    public String handleRequest(Test input, Context context) {
        if(input.getId() == null){
            throw new RuntimeException("error null id");
        }
        Test test = dynamoDBMapper.load(Test.class, input.getId());
        if(test == null) {
            throw new RuntimeException("error test does not exist");
        }
        dynamoDBMapper.delete(test);
        Test deletedTest = dynamoDBMapper.load(Test.class, input.getId());
        if(deletedTest == null){
            return "Success";
        }
        throw new RuntimeException("error during delete");
    }
}
