package packt.com.getitright.models;

public class Answer {
    private String mId;
    private String mText;
    public String getId(){
        return mId;
    }
    public String getText(){return mText;}
    public Answer (String id, String text){
        mId = id;
        mText = text;
    }
}
