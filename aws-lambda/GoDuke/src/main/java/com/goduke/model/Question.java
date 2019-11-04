package com.goduke.model;

import java.util.List;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@DynamoDBTable(tableName="Questions")
public class Question {

    private Integer number;
    private String type;
    private String language;
    private String question;
    private List<String> options;
    
    public Question() {}
    
    public Question(String json) {
    	Gson gson = new Gson();
    	Question request = gson.fromJson(json, Question.class);
    	this.number = request.getNumber();
    	this.type = request.getType();
    	this.language = request.getLanguage();
    	this.question = request.getQuestion();
    	this.options = request.getOptions();
    }
    
    @DynamoDBHashKey(attributeName="number")
    public Integer getNumber() { return number; }
    public void setNumber(Integer number) {this.number = number; }

    @DynamoDBAttribute(attributeName="type")
    public String getType() {return type; }
    public void setType(String type) { this.type = type; }

    @DynamoDBAttribute(attributeName="language")
    public String getLanguage() { return language; }
    public void setLanguage(String language) { this.language = language; }

    @DynamoDBAttribute(attributeName="question")
    public String getQuestion() { return question; }
    public void setQuestion(String question) { this.question = question; }

    @DynamoDBAttribute(attributeName="options")
    public List<String> getOptions() { return options; }
    public void setOptions(List<String> options) { this.options = options; }
    
    public String toString() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(this);
    }
}
