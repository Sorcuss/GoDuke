package com.goduke.validator;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedScanList;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.goduke.model.Candidate;
import com.goduke.model.Recruiter;
import com.google.gson.internal.$Gson$Types;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class EmailValidator {
    private static DynamoDBMapper dbMapper = new DynamoDBMapper(AmazonDynamoDBClientBuilder.defaultClient());

    private static DynamoDBScanExpression getScanExpression(String email){
        HashMap<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
        eav.put(":val1", new AttributeValue().withS(email));
        return new DynamoDBScanExpression()
                .withFilterExpression("email = :val1").withExpressionAttributeValues(eav);
    }

    private static boolean checkUsersEmail(String id, String email, Class className, List<?> objects){
        try{
            Object idValue = className.getMethod("getId").invoke(objects.get(0));
            return idValue.equals(id);
        }catch(IllegalAccessException | NoSuchMethodException | InvocationTargetException e){
            return false;
        }
    }

    static boolean checkUniqueEmail(String email, String id, Class className) {
        if(email.equals("")){
            return false;
        }
        List<Recruiter> recruiters = dbMapper.scan(Recruiter.class, EmailValidator.getScanExpression(email));
        List<Candidate> candidates = dbMapper.scan(Candidate.class, EmailValidator.getScanExpression(email));
        if(recruiters.size() == 0 && candidates.size() == 0){
            return true;
        }
        if(id != null){
            boolean recTest = EmailValidator.checkUsersEmail(email, id, Recruiter.class, recruiters);
            boolean canTest = EmailValidator.checkUsersEmail(email, id, Candidate.class, candidates);
            return recTest || canTest;
        }
        return false;
    }

    static boolean checkEmailFormat(String email){
        String reqEx = "^(.+)@(.+)$";
        Pattern pattern = Pattern.compile(reqEx);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
