package com.goduke.validator;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.goduke.model.Recruiter;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RecruiterValidator {
    DynamoDBMapper dbMapper = new DynamoDBMapper(AmazonDynamoDBClientBuilder.defaultClient());

    public static boolean validate(Recruiter recruiter){
        RecruiterValidator recruiterValidator = new RecruiterValidator();
        return EmailValidator.checkEmailFormat(recruiter.getEmail()) &&
                recruiterValidator.checkNullField(recruiter) &&
                EmailValidator.checkUniqueEmail(recruiter.getEmail(), recruiter.getId());
    }

    private boolean checkNullField(Recruiter recruiter){
        return recruiter.getEmail() != null && recruiter.getFirstname() != null && recruiter.getLastname() != null && recruiter.getPassword() != null;
    }
}

