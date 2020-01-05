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

public class AddTest implements RequestHandler<Test, Test> {
    DynamoDBMapper dynamoDBMapper = new DynamoDBMapper(AmazonDynamoDBClientBuilder.defaultClient());

    @Override
    public Test handleRequest(Test input, Context context) {
        if(!TestValidator.validate(input)){
            throw new RuntimeException("test have invalid data");
        }
        dynamoDBMapper.save(input);
        return input;
    }
}
