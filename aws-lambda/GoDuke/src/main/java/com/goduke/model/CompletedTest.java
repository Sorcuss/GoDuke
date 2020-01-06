package com.goduke.model;

public class CompletedTest extends TestWrapper {
    private String name;
    private boolean isRated;
    private int score;

    public CompletedTest(String name, int score, boolean isRated) {
        this.name = name;
        this.score = score;
        this.isRated = isRated;
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
}
