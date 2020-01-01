package com.goduke.converter;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverted;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;
import com.goduke.model.Candidate;
import com.google.gson.Gson;

import java.lang.annotation.Annotation;

public class CandidateTypeConverter implements DynamoDBTypeConverter<String, Candidate> {

    @Override
    public String convert(Candidate object) {
        Gson gson = new Gson();
        return gson.toJson(object);
    }

    @Override
    public Candidate unconvert(String object) {
        Gson gson = new Gson();
        return gson.fromJson(object, Candidate.class);
    }
}
