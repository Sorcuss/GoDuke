package com.goduke.function.test;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.goduke.model.Test;

import java.util.HashMap;
import java.util.List;

public class GetRecruiterTests implements RequestHandler<String, List<Test>> {
    DynamoDBMapper dynamoDBMapper = new DynamoDBMapper(AmazonDynamoDBClientBuilder.defaultClient());

    @Override
    public List<Test> handleRequest(String input, Context context) {
        if(input == null || input.equals("")){
            throw new RuntimeException("Email is empty");
        }
        return dynamoDBMapper.scan(Test.class, getScanExpression(input));
    }

    private DynamoDBScanExpression getScanExpression(String recruiter){
        HashMap<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
        eav.put(":val1", new AttributeValue().withS(recruiter));
        return new DynamoDBScanExpression()
                .withFilterExpression("recruiter = :val1").withExpressionAttributeValues(eav);
    }
}
