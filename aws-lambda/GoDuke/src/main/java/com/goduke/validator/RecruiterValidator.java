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
        return recruiterValidator.checkEmailFormat(recruiter.getEmail()) &&
                recruiterValidator.checkNullField(recruiter) &&
                recruiterValidator.checkUniqueEmail(recruiter.getEmail(), recruiter.getId());
    }

     private boolean checkUniqueEmail(String email, String id){
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
         else if(recruiters.size() == 1){
             if(id != null)
                return recruiters.get(0).getId().equals(id);
             return false;
         }
         else {
             return false;
         }
     }

     private boolean checkEmailFormat(String email){
          if(email == null){
              return false;
          }
          String emailReqEx = "^(.+)@(.+)$";
          Pattern pattern = Pattern.compile(emailReqEx);
          Matcher matcher = pattern.matcher(email);
          return matcher.matches();
     }

    private boolean checkNullField(Recruiter recruiter){
        return recruiter.getEmail() != null && recruiter.getFirstname() != null && recruiter.getLastname() != null && recruiter.getPassword() != null;
    }
}

