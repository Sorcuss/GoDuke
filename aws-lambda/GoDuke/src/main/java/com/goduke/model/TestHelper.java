package com.goduke.model;

import java.util.List;

public class TestHelper {
    private String id;
    private List<String> languages;
    private String name;
    private List<String> questionsIds;
    private String recruiterId;
    private List<String> candidatesIds;

    public TestHelper(){

    }

    public TestHelper(String id, List<String> languages, String name, List<String> questionsIds, String recruiterId, List<String> candidatesIds) {
        this.id = id;
        this.languages = languages;
        this.name = name;
        this.questionsIds = questionsIds;
        this.recruiterId = recruiterId;
        this.candidatesIds = candidatesIds;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getLanguages() {
        return languages;
    }

    public void setLanguages(List<String> languages) {
        this.languages = languages;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getQuestionsIds() {
        return questionsIds;
    }

    public void setQuestionsIds(List<String> questionsIds) {
        this.questionsIds = questionsIds;
    }

    public String getRecruiterId() {
        return recruiterId;
    }

    public void setRecruiterId(String recruiterId) {
        this.recruiterId = recruiterId;
    }

    public List<String> getCandidatesIds() {
        return candidatesIds;
    }

    public void setCandidatesIds(List<String> candidatesIds) {
        this.candidatesIds = candidatesIds;
    }
}
