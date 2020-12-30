package packt.com.getitright.models;

import android.support.annotation.Nullable;

import java.util.ArrayList;

public class Question {
    private String mText;

    private String mUri;
    private String mCorrectAnswer;
    private String mAnswer;
    private ArrayList<Answer> mPossibleAnswers;
    public String getText(){
        return mText;
    }
    public String getUri(){
        return mUri;
    }
    public String getCorrectAnswer(){
        return mCorrectAnswer;
    }
    public String getAnswer(){
        return mAnswer;
    }
    public Question (String text, String uri, String correctAnswer){
        mText = text;
        mUri = uri;
        mCorrectAnswer = correctAnswer;
    }
    @SuppressWarnings("UnusedReturnValue")
    public Answer addAnswer(String id, String text){
        if (mPossibleAnswers==null){
            mPossibleAnswers = new ArrayList<Answer>();
        }
        Answer answer = new Answer(id,text);
        mPossibleAnswers.add(answer);
        return answer;
    }

    @Nullable
    public ArrayList<Answer> getPossibleAnswers(){
        return mPossibleAnswers;
    }
}
