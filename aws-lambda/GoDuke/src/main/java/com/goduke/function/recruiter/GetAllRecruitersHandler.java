package com.goduke.function.recruiter;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.lambda.runtime.Context;
import com.goduke.model.Recruiter;

import java.util.List;

public class GetAllRecruitersHandler {
    private DynamoDBMapper dynamoDBMapper = new DynamoDBMapper(AmazonDynamoDBClientBuilder.defaultClient());
    public List<Recruiter>  handleRequest(Recruiter recruiter, Context context){
        return dynamoDBMapper.scan(Recruiter.class, new DynamoDBScanExpression());
    }
}
