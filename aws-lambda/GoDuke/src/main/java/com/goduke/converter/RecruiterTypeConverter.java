package com.goduke.converter;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;
import com.goduke.model.Recruiter;
import com.google.gson.Gson;

public class RecruiterTypeConverter implements DynamoDBTypeConverter<String, Recruiter> {

    @Override
    public String convert(Recruiter object) {
        Gson gson = new Gson();
        return gson.toJson(object);
    }

    @Override
    public Recruiter unconvert(String object) {
        Gson gson = new Gson();
        return gson.fromJson(object, Recruiter.class);
    }
}
