package com.goduke.converter;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverted;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;
import com.goduke.model.Test;
import com.google.gson.Gson;

import java.lang.annotation.Annotation;

public class TestTypeConverter implements DynamoDBTypeConverter<String, Test> {

    @Override
    public String convert(Test object) {
        Gson gson = new Gson();
        return gson.toJson(object);
    }

    @Override
    public Test unconvert(String object) {
        Gson gson = new Gson();
        return gson.fromJson(object, Test.class);
    }
}
