package com.goduke.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import java.io.Serializable;
import java.util.List;

@DynamoDBTable(tableName = "test")
public class Test implements Serializable {
    private Integer id;
    private List<String> languages;
    private String name;
    private List<Question> questions;

}
