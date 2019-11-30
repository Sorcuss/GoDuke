package com.goduke.function.test;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.goduke.model.Test;

public class DeleteTest implements RequestHandler<Test, String> {
    DynamoDBMapper dynamoDBMapper = new DynamoDBMapper(AmazonDynamoDBClientBuilder.defaultClient());

    @Override
    public String handleRequest(Test input, Context context) {
        Test test = dynamoDBMapper.load(Test.class, input.getId());
        if(test == null) {
            return "Test with this id does not exist";
        }
        dynamoDBMapper.delete(test);
        Test deletedTest = dynamoDBMapper.load(Test.class, input.getId());
        if(deletedTest == null){
            return "Success";
        }
        return "Error";
    }
}
