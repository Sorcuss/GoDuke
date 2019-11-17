package com.goduke.util;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.goduke.model.Recruiter;

import java.util.HashMap;
import java.util.List;

public class CheckUnique  {
    public static boolean checkRecruiterEmail(DynamoDBMapper dbMapper, String email) {
        if(email == "" || email == null){
            return false;
        }
        HashMap<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
        eav.put(":val1", new AttributeValue().withS(email));
        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
                .withFilterExpression("email = :val1").withExpressionAttributeValues(eav);
        List<Recruiter> recruiters = dbMapper.scan(Recruiter.class, scanExpression);
        if(recruiters.size() == 0){
            return true;
        }
        return false;
    }
}
