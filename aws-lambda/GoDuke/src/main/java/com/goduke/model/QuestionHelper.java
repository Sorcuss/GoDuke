package com.goduke.model;

public class QuestionHelper {
    private String testId;
    private String questionId;

    public QuestionHelper(String testId, String questionId) {
        this.testId = testId;
        this.questionId = questionId;
    }

    public String getTestId() {
        return testId;
    }

    public void setTestId(String testId) {
        this.testId = testId;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }
}
