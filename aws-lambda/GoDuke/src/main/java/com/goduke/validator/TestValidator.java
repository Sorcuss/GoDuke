package com.goduke.validator;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.goduke.model.Question;
import com.goduke.model.Test;
import java.util.HashMap;
import java.util.List;

public class TestValidator {
    private static DynamoDBMapper dynamoDBMapper = new DynamoDBMapper(AmazonDynamoDBClientBuilder.defaultClient());
    private static DynamoDBScanExpression getScanExpression(String name){
        HashMap<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
        eav.put(":val1", new AttributeValue().withS(name));
        return new DynamoDBScanExpression()
                .withFilterExpression("testName = :val1").withExpressionAttributeValues(eav);
    }

    private static boolean checkUniqueName(String name, String id){
        if(name.equals("")){
            return false;
        }
        List<Test> objects = dynamoDBMapper.scan(Test.class, TestValidator.getScanExpression(name));
        if(objects.get(0).getId().equals(id)) return true;
        return objects.size() == 0;
    }

    public static boolean validate(Test test) {
        return TestValidator.checkNullField(test)
                && TestValidator.checkUniqueName(test.getTestName(), test.getId())
                && validateQuestion(test);
    }
    public static boolean validateUpdate(Test test) {
        return TestValidator.checkNullField(test)
                && validateQuestion(test);
    }

    public static boolean validateQuestion(Test test){
        List<Question> questions = test.getQuestions();
        for(Question question : questions){
            if(!QuestionValidator.validate(question)){
                return false;
            }
        }
        return true;
    }

    private static boolean checkNullField(Test test){
        return test.getLanguages().size() != 0
                && test.getTestName() != null
                && test.getQuestions().size() != 0
                && test.getRecruiter() != null;
    }

}
