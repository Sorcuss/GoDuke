package com.goduke.model;

public class CompletedTest extends TestWrapper {
    private String name;
    private boolean isRated;
    private int score;
    private int maxScore;

    public CompletedTest(String name, int score, boolean isRated, int maxScore) {
        this.name = name;
        this.score = score;
        this.isRated = isRated;
        this.maxScore = maxScore;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public boolean isRated() {
        return isRated;
    }

    public void setRated(boolean rated) {
        isRated = rated;
    }

    public int getMaxScore() {
        return maxScore;
    }

    public void setMaxScore(int maxScore) {
        this.maxScore = maxScore;
    }
}
