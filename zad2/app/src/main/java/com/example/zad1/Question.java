package com.example.zad1;

public class Question {
    private int questionID;
    private boolean trueAnswer;

    public Question(int questionID,boolean trueAnswer){
        this.questionID = questionID;
        this.trueAnswer = trueAnswer;
    }

    public boolean isTrueAnswer() {
        return trueAnswer;
    }

    public void setTrueAnswer(boolean trueAnswer) {
        this.trueAnswer = trueAnswer;
    }

    public int getQuestionId() {
        return questionID;
    }

    public void setQuestionId(int questionId) {
        this.questionID = questionId;
    }
}
