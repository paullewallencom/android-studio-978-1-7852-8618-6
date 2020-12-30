package packt.com.getitright.models;

import android.support.annotation.NonNull;

import java.util.ArrayList;

public class Quiz {

    private ArrayList<Question> mQuestions;
    public ArrayList<Question> getQuestions(){
        return mQuestions;
    }

    public Question addQuestion(String text, String uri, String correctAnswer){
        if (mQuestions==null){
            mQuestions = new ArrayList<Question>();
        }
        Question question = new Question(text,uri,correctAnswer);
        mQuestions.add(question);
        return question;
    }
}
