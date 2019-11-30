package com.goduke.converter;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;
import com.goduke.model.Candidate;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class CandidatesTypeConverter implements DynamoDBTypeConverter<String, List<Candidate>> {
    @Override
    public String convert(List<Candidate> candidates) {
        Gson gson = new Gson();
        return gson.toJson(candidates);
    }

    @Override
    public List<Candidate> unconvert(String object) {
        Type typeOfCandidateLst = new TypeToken<List<Candidate>>(){}.getType();
        Gson gson = new Gson();
        return gson.fromJson(object, typeOfCandidateLst);
    }
}
