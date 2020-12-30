package com.packt.wateryou.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class Drink implements Parcelable {
    public Date dateAndTime;
    public String comments;
    public String imageUri;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeLong(dateAndTime.getTime());
        out.writeString(comments);
        out.writeString(imageUri);
    }

    public static final Parcelable.Creator<Drink> CREATOR = new Parcelable.Creator<Drink>() {
        public Drink createFromParcel(Parcel in) {
            return new Drink(in);
        }

        public Drink[] newArray(int size) {
            return new Drink[size];
        }
    };

    public Drink(){
    }

    public Drink(Parcel in) {
        dateAndTime = new Date(in.readLong());
        comments = in.readString();
        imageUri = in.readString();
    }
}
