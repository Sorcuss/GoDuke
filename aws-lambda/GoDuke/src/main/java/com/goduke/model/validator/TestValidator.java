package com.goduke.model.validator;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.goduke.model.test.Question;
import com.goduke.model.test.Test;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

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
        if(objects.size() != 0 && id != null)
              if(objects.get(0).getId().equals(id)) return true;
        return objects.size() == 0;
    }

    public static boolean validate(Test test) {
        return TestValidator.checkNullField(test)
                && TestValidator.checkUniqueName(test.getTestName(), test.getId())
                && validateQuestion(test)
                && languagesValidation(test)
                && questionNumberValidation(test);
    }
    public static boolean validateUpdate(Test test) {
        return TestValidator.checkNullField(test)
                && validateQuestion(test);
    }

    private static boolean validateQuestion(Test test){
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

    public static boolean questionNumberValidation(Test test) {
        List<Question> enQuestions = test.getQuestions().stream()
                .filter(question -> question.getLanguage().equals("en"))
                .collect(Collectors.toList());
        List<Question> plQuestions = test.getQuestions().stream()
                .filter(question -> question.getLanguage().equals("pl"))
                .collect(Collectors.toList());
        if (test.getLanguages().size() == 2) {
            return enQuestions.size() == plQuestions.size();
        } else {
            String language = test.getLanguages().get(0);
            if (language.equals("pl")) return enQuestions.size() == 0;
            else return plQuestions.size() == 0;
        }
    }

    private static boolean languagesValidation(Test test){
        int languagesQuantity = test.getLanguages().size();
        boolean languageQuantityFlag = languagesQuantity == 1 || languagesQuantity == 2;
        boolean correctLanguageFlag = true;
        for(String language : test.getLanguages()){
            if(correctLanguageFlag)
                 correctLanguageFlag = language.equals("pl") || language.equals("en");
        }
        return correctLanguageFlag && languageQuantityFlag;
    }
}
