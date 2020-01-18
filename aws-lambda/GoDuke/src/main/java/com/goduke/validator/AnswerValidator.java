package com.goduke.validator;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.goduke.model.Answer;
import com.goduke.model.Test;
import com.sun.org.apache.xpath.internal.objects.XNull;

import java.util.HashMap;
import java.util.List;

public class AnswerValidator {

    public static boolean validate(Answer answer){
        return checkExistAnswer(answer);
    }

    private static DynamoDBScanExpression getScanExpression(String candidate){
        HashMap<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
        eav.put(":val1", new AttributeValue().withS(candidate));
        return new DynamoDBScanExpression()
                .withFilterExpression("candidate = :val1").withExpressionAttributeValues(eav);
    }

    private static boolean checkExistAnswer(Answer answer){
        DynamoDBMapper dynamoDBMapper = new DynamoDBMapper(AmazonDynamoDBClientBuilder.defaultClient());
        List<Answer> candidateSAnswers = dynamoDBMapper.scan(Answer.class, getScanExpression(answer.getCandidate()));
        if(candidateSAnswers != null){
            Answer existAnswer = candidateSAnswers.stream()
                    .filter(item -> item.getTest().getId().equals(answer.getTest().getId()))
                    .findAny()
                    .orElse(null);
            if (answer.getId() != null && existAnswer != null) {
                return answer.getId().equals(existAnswer.getId());
            }
            return existAnswer == null;
        }
        return true;
    }
}
