package com.goduke.validator;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedScanList;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
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
        List<?> objects = dbMapper.scan(className, EmailValidator.getScanExpression(email));
        if(objects.size() == 0){
            return true;
        }
        if(id != null){
            return checkUsersEmail(id,email, className, objects);
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
