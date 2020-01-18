package com.goduke.converter;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;
import com.goduke.model.test.Question;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class QuestionsTypeConverter implements DynamoDBTypeConverter<String, List<Question>> {

    @Override
    public String convert(List<Question> questions) {
        Gson gson = new Gson();
        return gson.toJson(questions);
    }

    @Override
    public List<Question> unconvert(String object) {
        Type typeOfQuestionList = new TypeToken<List<Question>>(){}.getType();
        Gson gson = new Gson();
        return gson.fromJson(object, typeOfQuestionList);
    }
}
