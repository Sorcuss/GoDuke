package com.goduke.model.test;

import java.util.List;

public class QuestionValidator {
    public static boolean validate(Question question){
        QuestionValidator questionValidator = new QuestionValidator();
        return questionValidator.checkNull(question)
                && questionValidator.checkType(question.getType())
                && questionValidator.checkOptions(question.getOptions(), question.getType());
    }

    private boolean checkType(String type){
        return type.equals("O") || type.equals("W") || type.equals("L");

    }

    private boolean checkOptions(List<String> options, String type){
        if(!type.equals("W")){
            if(options != null) {
                return options.size() == 0;
            }
            return true;
        }else{
            if(options == null){
                return false;
            }
            return options.size() != 0;
        }
    }

    private boolean checkNull(Question question){
        return question.getLanguage() != null
                && question.getQuestion() != null
                && question.getType() != null;
    }
}
