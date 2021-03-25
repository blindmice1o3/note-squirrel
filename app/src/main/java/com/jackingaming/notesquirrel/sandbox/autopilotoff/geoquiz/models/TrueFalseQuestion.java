package com.jackingaming.notesquirrel.sandbox.autopilotoff.geoquiz.models;

public class TrueFalseQuestion {
    private int question;
    private boolean trueQuestion;

    public TrueFalseQuestion(int question, boolean trueQuestion) {
        this.question = question;
        this.trueQuestion = trueQuestion;
    }

    public int getQuestion() {
        return question;
    }

    public void setQuestion(int question) {
        this.question = question;
    }

    public boolean isTrueQuestion() {
        return trueQuestion;
    }

    public void setTrueQuestion(boolean trueQuestion) {
        this.trueQuestion = trueQuestion;
    }
}