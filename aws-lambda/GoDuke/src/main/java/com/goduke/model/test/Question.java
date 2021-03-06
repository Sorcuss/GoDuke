package com.goduke.model.test;

import com.google.gson.Gson;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class Question implements Serializable {

    private String id;
    private String type;
    private String language;
    private String question;
    private List<String> options;

    public Question(){
    }

    public Question(String id, String type, String language, String question, List<String> options) {
        this.id = id;
        this.type = type;
        this.language = language;
        this.question = question;
        this.options = new ArrayList<>(options);
    }

    public Question(Question question) {
        this.type = question.getType();
        this.language = question.getLanguage();
        this.question = question.getQuestion();
        this.options = question.getOptions();
    }


    public Question(String json) {
        Gson gson = new Gson();
        Question request = gson.fromJson(json, Question.class);
        this.id = request.getId();
        this.type = request.getType();
        this.language = request.getLanguage();
        this.question = request.getQuestion();
        this.options = request.getOptions();
    }

    public String getId() { return id; }
    public void setId(String number) {this.id = number; }

    public String getType() {return type; }
    public void setType(String type) { this.type = type; }

    public String getLanguage() { return language; }
    public void setLanguage(String language) { this.language = language; }

    public String getQuestion() { return question; }
    public void setQuestion(String question) { this.question = question; }

    public List<String> getOptions() { return options; }
    public void setOptions(List<String> options) { this.options = options; }
}
