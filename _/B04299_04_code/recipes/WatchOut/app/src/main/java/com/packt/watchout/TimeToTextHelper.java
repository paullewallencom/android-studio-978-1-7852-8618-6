package com.packt.watchout;

import android.content.Context;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by mike on 05-06-15.
 */
public class TimeToTextHelper {

    public String[] hours=
            {"twelve", "one", "two", "three","four","five","six","seven", "eight", "nine", "ten",
             "eleven", "twelve", "thirtheen", "fourteen", "fifteen", "sixteen", "seventeen",
            " eightteen", "nineteen", "twenty"};

    public String quart = "quarter";
    public String half ="half past";

    public String past = "past";
    public String before = "to";

    public String translate(Context context, Date time){

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);

        int hour = calendar.get(Calendar.HOUR);
        int minute = calendar.get(Calendar.MINUTE);

        hour++;
        if (hour>12){
            hour = hour-12;
        }
        String hourText = hourToText(hour);

        if (minute<=10){

        }
        else if (minute <= 18){

        }
        else if (minute <= )

    }

    public String hourToText(int hour){
        return hours[hour];
    }
}
